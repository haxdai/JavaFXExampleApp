package mx.edu.itt.provider.repository.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mx.edu.itt.model.Product;
import mx.edu.itt.model.User;
import mx.edu.itt.provider.repository.ProductRepository;
import mx.edu.itt.provider.repository.UserRepository;

/**
 * Implementación de repositorio de productos con almacenamiento a través de JDBC.
 * @author hasdai
 *
 */
public class SQLProductRepository implements ProductRepository {
    private static final Logger LOG = Logger.getLogger(SQLProductRepository.class.getName());
    private static SQLProductRepository instance;
    private Connection connection;
    private boolean driverLoaded = false;

    /**
     * Obtiene una instancia de SQLProvider
     * @return SQLProvider
     */
    public static SQLProductRepository getInstance() {
        if (instance == null) {
            instance = new SQLProductRepository();
        }
        return instance;
    }

    private SQLProductRepository() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            driverLoaded = true;
        } catch (Exception cnfe) {
            LOG.log(Level.SEVERE, "Error al realizar la conexión", cnfe);
        }
    }

    /**
     * Crea una conexión a la base de datos MySQL
     * @param url URL de conexión a la base de datos
     * @param user Usuario
     * @param pass Password
     * @return true si la conexión se ha realizado
     */
    public boolean connect(String url, String user, String pass) {
        if (driverLoaded) {
            try {
                connection = DriverManager.getConnection(url, user, pass);
                return true;
            } catch (SQLException sqe) {
                LOG.log(Level.SEVERE, "Error al realizar la conexión", sqe);
            }
        }
        return false;
    }

    /**
     * Se desconecta de la base de datos MySQL
     * @return true si se ha podido desconectar
     */
    public boolean disconnect() {
        if (null != connection) {
            try {
                connection.close();
                return true;
            } catch (SQLException sqe) {
                LOG.log(Level.SEVERE, "Error al realizar la desconexión", sqe);
            }
        }
        return false;
    }

    /**
     * Ejecuta una consulta tipo select a la base de datos.
     * @param st Sentencia SQL a ejecutar.
     * @return ResultSet con los resultados de la consulta.
     */
    public ResultSet executeSelectQuery(PreparedStatement st) {
        try {
            return st.executeQuery();
        } catch (SQLException sqe) {
            LOG.log(Level.SEVERE, "Error al realizar la consulta", sqe);
        }
        return null;
    }

    /**
     * Ejecuta una consulta de tipo actualización a la base de datos.
     * @param st Sentencia SQL a ejecutar.
     * @return número de registros afectados por la actualización.
     */
    public int executeUpdateQuery(PreparedStatement st) {
        try {
            return st.executeUpdate();
        } catch (SQLException sqe) {
            LOG.log(Level.SEVERE, "Error al realizar la actualización", sqe);
        }
        return 0;
    }

    /**
     * Devuelve la conexión asociada al DataProvider.
     * @return Connection
     */
    public Connection getConnection() {
        return connection;
    }

    @Override
	public Product save(Product sample) {
		if (null != getConnection() && null != sample && sample.isValid()) {
			Product ret = findById(sample.getId());
			String query;
			
			if (null == ret) {
				query = "INSERT INTO producto (nombre, precio, tipo, "
						+ "marca, caducidad)"
                        + " values (?, ?, ?, ?, ?)";
			} else {
				query = "UPDATE producto SET nombre = ?, precio = ?, tipo = ?, "
						+ "marca = ?, caducidad = ? "
						+ "WHERE idproducto = ?";
			}
			
			try (PreparedStatement st = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				st.setString(1, sample.getName());
	            st.setDouble(2, sample.getPrice());
	            st.setInt(3, sample.getType());
	            st.setString(4, sample.getBrand());
	            st.setDate(5, new Date(sample.getExpiration().getTime()));

	            if (null != ret) {
	            	st.setInt(6, sample.getId());
	            }

	            st.executeUpdate();
	            
	            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    sample.setId(generatedKeys.getInt(1));
	                }
	            }
	            return sample;
			} catch (SQLException sqe) {
				LOG.log(Level.SEVERE, "No se pudo almacenar el objeto en la base de datos", sqe);
			}
		}
		
		return null;
	}

	@Override
	public List<Product> findAll() {
		List<Product> ret = new ArrayList<>();
	    
		try (Statement stmt = connection.createStatement()) {
			try (ResultSet rs = stmt.executeQuery("SELECT * FROM producto")) {
				while (rs.next()) {
					Product prod = new Product();
					prod.setId(rs.getInt(1));
					prod.setName(rs.getString(2));
					prod.setPrice(rs.getDouble(3));
					prod.setType(rs.getInt(4));
					prod.setBrand(rs.getString(5));
					
					Timestamp dt = rs.getTimestamp(4);
					prod.setExpiration(new java.util.Date(dt.getTime()));
				
					ret.add(prod);
		        }
			}
	    } catch (SQLException e ) {
	    	LOG.log(Level.SEVERE, "No se pudo ejecutar la consulta", e);
	    }
		
		return ret;
	}

	@Override
	public Product remove(Product sample) {
		return removeById(sample.getId());
	}

	@Override
	public Product removeById(Integer id) {
		Product ret = findById(id);
		try (PreparedStatement st = connection.prepareStatement("DELETE FROM producto WHERE idproducto = ?")) {
			st.setInt(1, id);
            st.executeUpdate();
		} catch (SQLException sqe) {
			LOG.log(Level.SEVERE, "No se pudo ejecutar la actualización", sqe);
		}
		
		return ret;
	}

	@Override
	public Product findById(Integer id) {
		try (PreparedStatement st = connection.prepareStatement("SELECT * FROM producto WHERE idproducto = ?")) {
			st.setInt(1, id);
        	
        	try (ResultSet rs = st.executeQuery()) {
	        	rs.next();
	             
	            Product prod = new Product();
	            prod.setName(rs.getString(2));
				prod.setPrice(rs.getDouble(3));
				prod.setType(rs.getInt(4));
				prod.setBrand(rs.getString(5));
				
				Timestamp dt = rs.getTimestamp(4);
				prod.setExpiration(new java.util.Date(dt.getTime()));
	            
	            return prod;
        	}
		} catch (SQLException sqe) {
            LOG.log(Level.SEVERE, "No se pudo almacenar el objeto en la base de datos", sqe);
        }
        return null;
	}

	@Override
	public void clear() {
        try (PreparedStatement st = connection.prepareStatement("DELETE FROM producto")) {
            st.executeUpdate();
        } catch (SQLException sqe) {
        	LOG.log(Level.SEVERE, "No se pudo ejecutar la actualización", sqe);
        }
	}
}
