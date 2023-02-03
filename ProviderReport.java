import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProviderReport {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("No se proporcionó ningún argumento");
            return;
        }
        int clientCode = Integer.parseInt(args[0]);

        try (Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/proveedores", "root", "123456")) {
            // Realizar una consulta a la tabla de proveedores
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id_proveedor, nombre, fecha_alta FROM proveedores WHERE id_cliente = " + clientCode);

            // Recolectar los resultados de la consulta en una lista de proveedores
            List<Provider> providers = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_proveedor");
                String name = resultSet.getString("nombre");
                Date date = resultSet.getDate("fecha_alta");
                providers.add(new Provider(id, name, date));
            }

            // Si no se encontraron proveedores para el cliente, mostrar un mensaje
            if (providers.isEmpty()) {
                System.out.println("El cliente no tiene proveedores asignados");
                return;
            }

            // Crear un archivo plano con la información de los proveedores
            try (FileWriter fileWriter = new FileWriter("providers_" + clientCode + ".txt")) {
                fileWriter.write("Código Proveedor, Nombre Proveedor, Fecha Alta\n");
                providers.stream().map(provider -> provider.id + "," + provider.name + "," + provider.date)
                        .collect(Collectors.toList()).forEach(provider -> {
                            try {
                                fileWriter.write(provider + "\n");
                            } catch (IOException e) {
                                System.out.println("Error al escribir en el archivo: " + e.getMessage());
                            }
                        });
                System.out.println("Reporte generado exitosamente");
            } catch (IOException e) {
                System.out.println("Error al escribir en el archivo: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión a la base de datos: " + e.getMessage());
        }
    }

    // Clase para representar un proveedor
    private static class Provider {
        int id;
        String name;
        Date date;

        public Provider(int id, String name, Date date) {
            this.id = id;
            this.name = name;
            this.date = date;
        }
    }
}