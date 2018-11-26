package mx.edu.itt.provider.repository.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mx.edu.itt.model.User;
import mx.edu.itt.provider.repository.UserRepository;

/**
 * Implementación de repositorio de usuarios con almacenamiento a través de JDBC.
 * @author hasdai
 *
 */
public class SQLUserRepository implements UserRepository {
    private static final Logger LOG = Logger.getLogger(SQLUserRepository.class.getName());
    private static SQLUserRepository instance;
    private Connection connection;
    private boolean driverLoaded = false;

    /**
     * Obtiene una instancia de SQLProvider
     * @return SQLProvider
     */
    public static SQLUserRepository getInstance() {
        if (instance == null) {
            instance = new SQLUserRepository();
        }
        return instance;
    }

    private SQLUserRepository() {
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
	public User save(User sample) {
		if (null != getConnection() && null != sample && sample.isValid()) {
			User ret = findById(sample.getId());
			String query;
			
			if (null == ret) {
				query = "INSERT INTO usuario (nombre, apellido, nacimiento, "
						+ "codigo_postal, ciudad, direccion, email)"
                        + " values (?, ?, ?, ?, ?, ?, ?)";
			} else {
				query = "UPDATE usuario SET nombre = ?, apellido = ?, nacimiento = ?, "
						+ "codigo_postal = ?, ciudad = ?, direccion = ?, email = ? "
						+ "WHERE idusuario = ?";
			}
			
			try (PreparedStatement st = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				st.setString(1, sample.getFirstName());
	            st.setString(2, sample.getLastName());
	            st.setDate(3, new Date(sample.getBirthDay().getTime()));
	            st.setInt(4, sample.getPostalcode());
	            st.setString(5, sample.getCity());
	            st.setString(6, sample.getAddress());
	            st.setString(7, sample.getEmail());
	            if (null != ret) {
	            	st.setInt(8, sample.getId());
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
	public List<User> findAll() {
		List<User> ret = new ArrayList<>();
	    
		try (Statement stmt = connection.createStatement()) {
			try (ResultSet rs = stmt.executeQuery("SELECT * FROM usuario")) {
				while (rs.next()) {
					User user = new User();
					user.setId(rs.getInt(1));
					user.setFirstName(rs.getString(2));
					user.setLastName(rs.getString(3));
					
					Timestamp dt = rs.getTimestamp(4);
					user.setBirthDay(new java.util.Date(dt.getTime()));
					user.setPostalcode(rs.getInt(5));
					user.setCity(rs.getString(6));
					user.setAddress(rs.getString(7));
					user.setEmail(rs.getString(8));
					
					ret.add(user);
		        }
			}
	    } catch (SQLException e ) {
	    	LOG.log(Level.SEVERE, "No se pudo ejecutar la consulta", e);
	    }
		
		return ret;
	}

	@Override
	public User remove(User sample) {
		return removeById(sample.getId());
	}

	@Override
	public User removeById(Integer id) {
		User ret = findById(id);
		
		try (PreparedStatement st = connection.prepareStatement("DELETE FROM usuario WHERE idusuario = ?")) {
			st.setInt(1, id);
            st.executeUpdate();
		} catch (SQLException sqe) {
			LOG.log(Level.SEVERE, "No se pudo ejecutar la actualización", sqe);
		}
		return ret;
	}

	@Override
	public User findById(Integer id) {
		try (PreparedStatement st = connection.prepareStatement("SELECT * FROM usuario WHERE idusuario = ?")) {
			st.setInt(1, id);
        	
        	ResultSet rs = st.executeQuery();
        	rs.next();
             
            User user = new User();
			user.setId(rs.getInt(1));
			user.setFirstName(rs.getString(2));
			user.setLastName(rs.getString(3));
			user.setBirthDay(rs.getDate(4));
			user.setPostalcode(rs.getInt(5));
			user.setCity(rs.getString(6));
			user.setAddress(rs.getString(7));
			user.setEmail(rs.getString(8));
            
            return user;
		} catch (SQLException sqe) {
			LOG.log(Level.SEVERE, "No se pudo almacenar el objeto en la base de datos", sqe);
		}

        return null;
	}

	@Override
	public void clear() {
		try (PreparedStatement st = connection.prepareStatement("DELETE FROM usuario")) {
			st.executeUpdate();
		} catch (SQLException sqe) {
			LOG.log(Level.SEVERE, "No se pudo ejecutar la actualización", sqe);
		}
	}
}
