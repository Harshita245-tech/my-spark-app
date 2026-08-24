import org.apache.spark.sql.SparkSession

object App {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("MyFirstSparkApp")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val df = Seq(
      ("Rakesh", "Engineering", 95000),
      ("Kavish", "Sales", 72000),
      ("Chetan", "Engineering", 88000)
    ).toDF("name", "dept", "salary")

    println("Employee Data:")
    df.show()

    println("Average Salary by Department:")
    val avgByDept = df.groupBy("dept").avg("salary")
    avgByDept.show()

    spark.stop()
  }
}
