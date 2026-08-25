import org.apache.spark.sql.SparkSession

object WordCount {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("WordCount")
      .master("local[*]")
      .getOrCreate()

    val text = spark.sparkContext.parallelize(
      Seq(
        "hello spark",
        "hello scala",
        "spark is fast"
      )
    )

    val wordCounts = text
      .flatMap(_.split("\\s+"))
      .map(word => (word, 1))
      .reduceByKey(_ + _)

    wordCounts.collect().foreach(println)

    spark.stop()
  }
}
