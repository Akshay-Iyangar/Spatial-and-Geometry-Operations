							Group 10 README

Assuming the configaration is done and the workers are up and running, the following commands can be used to run the respective operations:

Geometry Union
The following arguments must be provided from command line.
Argument 1: Example- "hdfs://master:54310/home/ddsuser/TestCase/test/UnionQueryTestData.csv"
Argument 2: Example- "hdfs://master:54310/home/ddsuser/TestCase/test/UnionQueryResult.csv"

Therefore, please run the following command :
./spark-submit --class <GroupId>.<ArtifactID>.<ClassName> --jars <Path to JTS jar file> --master <masterURL> <Path to SNAPSHOT jar file> <Argument1> <Argument2>

Geometry Convex Hull
The following arguments must be provided from command line.
Argument 1: Example- "hdfs://master:54310/home/ddsuser/TestCase/test/ConvexHullTestData.csv"
Argument 2: Example- "hdfs://master:54310/home/ddsuser/TestCase/test/ConvexHullResult.csv"

Therefore, please run the following command :
./spark-submit --class <GroupId>.<ArtifactID>.<ClassName> --jars <Path to JTS jar file> --master <masterURL> <Path to SNAPSHOT jar file> <Argument1> <Argument2> 

Geometry Farthest Pair
The following arguments must be provided from command line.
Argument 1: Example- "hdfs://master:54310/home/ddsuser/TestCase/test/FarthestPairTestData.csv"
Argument 2: Example- "hdfs://master:54310/home/ddsuser/TestCase/test/FarthestPairResult.csv"

Therefore, please run the following command :
./spark-submit --class <GroupId>.<ArtifactID>.<ClassName> --jars <Path to JTS jar file> --master <masterURL> <Path to SNAPSHOT jar file> <Argument1> <Argument2>

Geometry Closest Pair
The following arguments must be provided from command line.
Argument 1: Example- "hdfs://master:54310/home/ddsuser/TestCase/test/ClosestPairTestData.csv"
Argument 2: Example- "hdfs://master:54310/home/ddsuser/TestCase/test/ClosestPairResult.csv"

Therefore, please run the following command :
./spark-submit --class <GroupId>.<ArtifactID>.<ClassName> --jars <Path to JTS jar file> --master <masterURL> <Path to SNAPSHOT jar file> <Argument1> <Argument2>

Spatial Range Query
The following arguments must be provided from command line.
Argument 1: Example- "hdfs://master:54310/home/ddsuser/TestCase/test/RangeQueryTestData.csv"
Argument 2: Example- "hdfs://master:54310/home/ddsuser/TestCase/test/RangeQueryRectangle.csv"
Argument 3: Example- "hdfs://master:54310/home/ddsuser/TestCase/test/RangeQueryResult.csv"

Therefore, please run the following command :
./spark-submit --class <GroupId>.<ArtifactID>.<ClassName> --master <masterURL> <Path to SNAPSHOT jar file> <Argument1> <Argument2> <Argument3>

Spatial Join Query
The following arguments must be provided from command line.

Rectangle
Argument 1: Example- "hdfs://master:54310/home/ddsuser/TestCase/test/JoinQueryInput1.csv"
Argument 2: Example- "hdfs://master:54310/home/ddsuser/TestCase/test/JoinQueryInput2.csv"
Argument 3: Example- "hdfs://master:54310/home/ddsuser/TestCase/test/JoinQueryRectangeResult.csv"
Argument 4: rectangle

Point
Argument 1: Example- "hdfs://master:54310/home/ddsuser/TestCase/test/JoinQueryInput3.csv"
Argument 2: Example- "hdfs://master:54310/home/ddsuser/TestCase/test/JoinQueryInput2.csv"
Argument 3: Example- "hdfs://master:54310/home/ddsuser/TestCase/test/JoinQueryPointResult.csv"
Argument 4: point


Therefore, please run the following command :
./spark-submit --class <GroupId>.<ArtifactID>.<ClassName> --master <masterURL> <Path to SNAPSHOT jar file> <Argument1> <Argument2> <Argument3> <Argument4>





