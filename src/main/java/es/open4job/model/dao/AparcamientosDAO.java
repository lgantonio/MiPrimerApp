package es.open4job.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.sql.CallableStatement;

import es.open4job.model.vo.AparcamientosAccesosVO;

public class AparcamientosDAO extends GenericDAO {

	public AparcamientosDAO(String driver, String url, String user,
			String password) throws ClassNotFoundException, SQLException {
		super(driver, url, user, password);

		this.abrirConexion();

	}

	public void closeConnection() {
		this.cerrarConexion();
	}

	// Listado de aparcamientos
	public List<AparcamientosAccesosVO> getListadoAparcamientos() {

		List<AparcamientosAccesosVO> aparcamientos = new ArrayList<AparcamientosAccesosVO>();

		String query = "SELECT * FROM APARCAMIENTO_ACCESOS ORDER BY ID";

		Statement st = null;
		ResultSet rs = null;

		try {
			st = connection.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				int id = rs.getInt(1);
				float latitud = rs.getFloat(2);
				float longitud = rs.getFloat(3);
				String titulo = rs.getString(4);
				String icono = rs.getString(5);
				String tipo = rs.getString(6);

				AparcamientosAccesosVO aparcamiento = new AparcamientosAccesosVO(
						id, latitud, longitud, titulo, icono, tipo);
				aparcamientos.add(aparcamiento);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "SQLException : " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
			if (st != null) {
				try {
					st.close();
				} catch (Exception e) {
				}
			}
		}

		return aparcamientos;
	}

	// Obtiene los datos de un aparcamiento en concreto
	public AparcamientosAccesosVO getDatosaparcamiento(int idAparcamiento)
			throws ClassNotFoundException {

		AparcamientosAccesosVO aparcamiento = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;

		String query = "SELECT * FROM APARCAMIENTO_ACCESOS WHERE ID = ?";

		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, idAparcamiento);

			rst = pstmt.executeQuery();

			while (rst.next()) {
				int id = rst.getInt(1);
				float latitud = rst.getFloat(2);
				float longitud = rst.getFloat(3);
				String titulo = rst.getString(4);
				String icono = rst.getString(5);
				String tipo = rst.getString(6);

				aparcamiento = new AparcamientosAccesosVO(id, latitud,
						longitud, titulo, icono, tipo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "SQLException : " + e.getMessage());
		} finally {
			if (rst != null) {
				try {
					rst.close();
				} catch (Exception e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
				}
			}
		}
		return aparcamiento;
	}

	// Actualizar el valor del campo ICONO para un aparcamiento en concreto
	public int updateIconoAparcamiento(int idAparcamiento, String valor)
			throws ClassNotFoundException {

		PreparedStatement pstmt = null;
		int estado = 0;

		String query = "UPDATE APARCAMIENTO_ACCESOS SET ICONO=? WHERE ID=?";

		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, valor);
			pstmt.setInt(2, idAparcamiento);

			estado = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "SQLException : " + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
				}
			}
		}
		return estado;
	}

	// Insertar un nuevo aparcamiento
	public int insertAparcamiento(AparcamientosAccesosVO nuevoAparcamiento)
			throws ClassNotFoundException {

		PreparedStatement pstmt = null;
		int estado = 0;

		String query = "INSERT INTO APARCAMIENTO_ACCESOS VALUES (?, ?, ?, ?, ?, ?)";

		try {

			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, nuevoAparcamiento.getId());
			pstmt.setDouble(2, nuevoAparcamiento.getLatitud());
			pstmt.setDouble(3, nuevoAparcamiento.getLongitud());
			pstmt.setString(4, nuevoAparcamiento.getTitulo());
			pstmt.setString(5, nuevoAparcamiento.getIcono());
			pstmt.setString(6, nuevoAparcamiento.getTipo());

			estado = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "SQLException : " + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
				}
			}
		}
		return estado;
	}

	// Siguiente
	public int getSiguiente(int _valor) throws ClassNotFoundException {

		CallableStatement cstmt = null;
		int x = 0;

		try {
			// Llamada al procedimiento de ejemplo
			cstmt = connection.prepareCall("{? = call SIGUIENTE(?)}");

			// registro del parametro OUT
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);

			// registro del parametro INT
			cstmt.setInt(2, _valor);

			// ejecutar el procedimiento
			cstmt.executeQuery();

			x = cstmt.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "SQLException : " + e.getMessage());
		} finally {
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception e) {
				}
			}
		}
		return x;
	}

	// Anterior
	public int getAnterior(int _valor) throws ClassNotFoundException {

		CallableStatement cstmt = null;
		int x = 0;

		try {

			// Llamada al procedimiento de ejemplo
			cstmt = connection.prepareCall("{? = call ANTERIOR(?)}");

			// registro del parametro OUT
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);

			// registro del parametro INT
			cstmt.setInt(2, _valor);

			// ejecutar el procedimiento
			cstmt.executeQuery();

			x = cstmt.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "SQLException : " + e.getMessage());
		} finally {
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception e) {
				}
			}
		}

		return x;
	}

}
