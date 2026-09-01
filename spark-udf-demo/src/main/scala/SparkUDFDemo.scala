import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.udf

object SparkUDFDemo {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Spark UDF Demo")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    // Create student data
    val students = Seq(
      Student(1, "Rahul", 80, 75, 90),
      Student(2, "Priya", 60, 70, 65),
      Student(3, "Arun", 90, 85, 95)
    )

    // Convert to DataFrame
    val df = students.toDF()

    println("Student Data:")
    df.show()

    // UDF to calculate average marks
    val averageMarks = udf((m1: Int, m2: Int, m3: Int) =>
      (m1 + m2 + m3) / 3.0
    )

    // Add average column
    val result = df.withColumn(
      "average",
      averageMarks($"marks1", $"marks2", $"marks3")
    )

    // Function to calculate grade
    def calculateGrade(percentage: Double): String = {
      if (percentage >= 90) "A"
      else if (percentage >= 75) "B"
      else if (percentage >= 60) "C"
      else if (percentage >= 50) "D"
      else "F"
    }

    // Convert function into UDF
    val gradeUDF = udf(calculateGrade _)

    // Register UDF with Spark SQL
    spark.udf.register("calculateGrade", calculateGrade _)

    // Add grade column
    val finalResult = result.withColumn(
      "grade",
      gradeUDF($"average")
    )

    println("Final Result:")
    finalResult.show()

    // Create temporary view
    finalResult.createOrReplaceTempView("students")

    // Use registered UDF in Spark SQL
    val sqlResult = spark.sql("""
      SELECT id, name, average,
             calculateGrade(average) AS grade
      FROM students
    """)

    println("SQL Result:")
    sqlResult.show()

    spark.stop()
  }
}
