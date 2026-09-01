import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object BankAccountApp {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Bank Account")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    // Create bank account data
    val accounts = Seq(
      BankAccount("A01", "Rahul", 50000, 10000, 15000),
      BankAccount("A02", "Priya", 30000, 5000, 40000),
      BankAccount("A03", "Arun", 60000, 20000, 10000)
    )

    // Convert to DataFrame
    val df = accounts.toDF()

    println("Bank Account Data:")
    df.show()

    // Calculate final balance
    val result = df.withColumn(
      "finalBalance",
      when(
        $"withdraw" > ($"initialBalance" + $"deposit"),
        $"initialBalance" + $"deposit"
      ).otherwise(
        $"initialBalance" + $"deposit" - $"withdraw"
      )
    )

    // Check transaction status
    val finalResult = result.withColumn(
      "message",
      when(
        $"withdraw" > ($"initialBalance" + $"deposit"),
        "Insufficient Balance"
      ).otherwise(
        "Transaction Successful"
      )
    )

    println("Final Result:")
    finalResult.show()

    spark.stop()
  }
}
