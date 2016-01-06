package edu.asu.cse512;


import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.spark.SparkConf;


import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.operation.union.CascadedPolygonUnion;


public class PolygonUnion implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -818365282654451537L;


	public static boolean GeometryUnion(String InputLocation,String OutputLocation) throws IOException, URISyntaxException{


		JavaSparkContext sc = Union.sparkContext;
		JavaRDD<String> inputRect = sc.textFile(InputLocation);
		JavaRDD<Geometry> mapRect = inputRect.mapPartitions(new FlatMapFunction<Iterator<String>, Geometry>()  {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Double rectX1,rectX2,rectY1,rectY2;
			int numberofrect;
			public Iterable<Geometry> call(Iterator<String> arg0) throws Exception {
				// TODO Auto-generated method stub
				ArrayList<Geometry> currPoly=new ArrayList<Geometry>();
				ArrayList<Geometry> actualPoly=new ArrayList<Geometry>();
				while(arg0.hasNext()){
					String coord=arg0.next();

					String[] tmp=coord.split(",");
					if(!tmp[0].equalsIgnoreCase("x"))
						rectX1=Double.parseDouble(tmp[0]);
					else
						continue;
					if(!tmp[1].equalsIgnoreCase("y"))
						rectY1=Double.parseDouble(tmp[1]);
					else
						continue;
					if(!tmp[2].equalsIgnoreCase("x"))
						rectX2=Double.parseDouble(tmp[2]);
					else
						continue;
					if(!tmp[3].equalsIgnoreCase("y"))
						rectY2=Double.parseDouble(tmp[3]);
					else
						continue;
					//System.out.println("x1:"+rectX1+" y1:"+rectY1+" x2:"+rectX2+" y2:"+rectY2);
					actualPoly.add(new GeometryFactory().createPolygon(new Coordinate[]{new Coordinate(rectX1,rectY1),
							new Coordinate(rectX1,rectY2),new Coordinate(rectX2,rectY2)
							,new Coordinate(rectX2,rectY1),new Coordinate(rectX1,rectY1)}));
				}
				CascadedPolygonUnion casd=new CascadedPolygonUnion((Collection<Geometry>)actualPoly);
				Geometry unionGeo=casd.union();
				numberofrect=unionGeo.getNumGeometries();
				//System.out.println(numberofrect);
				for(int i=0;i<numberofrect;i++){
					currPoly.add((Geometry)unionGeo.getGeometryN(i));
				}
				return currPoly;
			}
		});
		/*DistributedFileSystem dfs = new DistributedFileSystem();*/
		/*dfs.initialize(new URI("hdfs://master:54310"), new Configuration());*/
		
		/*Path path = new Path("/rectUnionPart");
		if (dfs.exists(path)) dfs.delete(path);*/
		//else dfs.create(path);
		
		int index=OutputLocation.lastIndexOf("/");
		String hadoopPath=OutputLocation.substring(0, index);
		String intermediateResultpath=hadoopPath+"/IntermediatePartialUnionResult";
		FileToCsv.deleteExistingFile(intermediateResultpath);
		mapRect.saveAsTextFile(intermediateResultpath);
		JavaRDD<Geometry> intermediateRect=mapRect.repartition(1);
		JavaRDD<Geometry> reducePoly = intermediateRect.mapPartitions(new FlatMapFunction<Iterator<Geometry>, Geometry>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Iterable<Geometry> call(Iterator<Geometry> arg0) throws Exception {
				ArrayList<Geometry> currPoly=new ArrayList<Geometry>();
				ArrayList<Geometry> actualPoly=new ArrayList<Geometry>();
				int numberofrect;
				// TODO Auto-generated method stub
				while(arg0.hasNext()){
					actualPoly.add(arg0.next());
				}
				CascadedPolygonUnion casd=new CascadedPolygonUnion((Collection<Geometry>)actualPoly);
				Geometry unionGeo=casd.union();
				numberofrect=unionGeo.getNumGeometries();
				for(int i=0;i<numberofrect;i++){
					currPoly.add((Geometry)unionGeo.getGeometryN(i));
				}
				return currPoly;
			}
		});
		List<Geometry> finaloutput=	reducePoly.collect();
		List<Point> sortedPoints=new ArrayList<Point>();
		List<String> sortedString=new ArrayList<String>();
		for(Geometry finalrect:finaloutput){
			String finaloutput1="";
			Coordinate[] finalcoord=finalrect.getCoordinates();
			for(int i=0;i<finalcoord.length-1;i++){
				finaloutput1+=finalcoord[i].x+","+finalcoord[i].y+"\n";
				sortedPoints.add(new Point(finalcoord[i].x,finalcoord[i].y));
			};
			//System.out.println(finaloutput1);	
		}
		Collections.sort(sortedPoints, new Comparator<Point>() {

			public int compare(Point o1, Point o2) {
				// TODO Auto-generated method stub
				int diff;
				diff=Double.compare(o1.getX(),o2.getX());
				if(diff==0){
					diff=Double.compare(o1.getY(), o2.getY());
				}
				return diff;
			}
		});
		for(Point sortedPt:sortedPoints){
			sortedString.add(sortedPt.getX()+","+sortedPt.getY());
		}
		JavaRDD<String> saveOutput=sc.parallelize(sortedString).repartition(1);
		/*Path path1 = new Path(OutputLocation);*/
		/*if (dfs.exists(path1)) dfs.delete(path1);*/
		

		index=OutputLocation.lastIndexOf("/");
		hadoopPath=OutputLocation.substring(0, index);
		intermediateResultpath=hadoopPath+"/IntermediateUnionResult";

		FileToCsv.deleteExistingFile(intermediateResultpath);
		saveOutput.saveAsTextFile(intermediateResultpath);

		FileToCsv.fileToCsv(intermediateResultpath, OutputLocation);
		//saveOutput.saveAsTextFile("/home/vishal/Desktop/output.txt");
		return true;
	}

}

class Point implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -213947372749553656L;
	/**
	 * 
	 */
	double x,y;
	Point(double x,double y){
		this.x=x;
		this.y=y;
	}
	double getX(){
		return x;
	}
	double getY(){
		return y;
	}
}