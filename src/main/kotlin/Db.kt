import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class Db() {
    val url = "jdbc:sqlite:items.db"

    fun connect(): Connection {
    return DriverManager.getConnection(url)
    }

    fun create() {
        connect().use { connection ->
            connection.createStatement().use { statement ->
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS items (id INT, title TEXT, desc TEXT, date TEXT, prty TEXT, status TEXT)")
            }
        }
    }

    fun insert(title: String, desc: String, date: String, prty: String, id: Int) {
        connect().use { connection ->
            val sql = "INSERT INTO items (id, title, desc, date, prty, status) VALUES (?, ?, ?, ?, ?, 'TO DO')"

            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setInt(1, id)
                preparedStatement.setString(2, title)
                preparedStatement.setString(3, desc)
                preparedStatement.setString(4, date)
                preparedStatement.setString(5, prty)

                preparedStatement.executeUpdate()
            }
        }
    }

    fun fetchItems(): List<Item> {
        val items = mutableListOf<Item>()

        connect().use { connection ->
            connection.createStatement().use { statement ->
                val resultSet: ResultSet = statement.executeQuery("SELECT * FROM items")

                while (resultSet.next()) {
                    val id = resultSet.getInt("id")
                    val title = resultSet.getString("title")
                    val desc = resultSet.getString("desc")
                    val date = resultSet.getString("date")
                    val prty = resultSet.getString("prty")
                    val status = resultSet.getString("status")

                    val item = Item(title, desc, date, prty, status, id)
                    items.add(item)
                }
            }
        }

        return items
    }

    fun update(w: String, n: Int, new: String) {
        connect().use { connection ->
            val sql = when (w) {
                "title" -> "UPDATE items SET title = ? WHERE id = ?"
                "desc" -> "UPDATE items SET desc = ? WHERE id = ?"
                "date" -> "UPDATE items SET date = ? WHERE id = ?"
                "prty" -> "UPDATE items SET prty = ? WHERE id = ?"
                else -> "UPDATE items SET status = ? WHERE id = ?"
            }

            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setString(1, new)
                preparedStatement.setInt(2, n)

                preparedStatement.executeUpdate()
            }
        }
    }

    fun delete(n: Int) {
        connect().use { connection ->
            connection.createStatement().use { statement ->
                statement.executeUpdate("DELETE FROM items WHERE id = '$n'")
            }
        }
    }
}
