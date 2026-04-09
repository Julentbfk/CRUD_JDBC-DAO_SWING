package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Conexion {
	
    private static final Logger logger = LoggerFactory.getLogger(Conexion.class);
    private static final Properties props = new Properties();
    
    private static HikariDataSource dataSource;
    
    public static Connection conectar(String user, String pass) throws SQLException { 
     
    	try (InputStream input = Conexion.class
                .getClassLoader()
                .getResourceAsStream("database.properties")) {

            if (input == null) {
                throw new RuntimeException("No se encontró el archivo database.properties");
            }

            props.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Error cargando configuracion", e);
        }

        String url;

        // URL
        if (System.getenv("DB_URL_escuela") != null){
            url = System.getenv("DB_URL_escuela");
            logger.info("DB_URL desde ENV");
        } else {
            url = props.getProperty("CONEXION");
            logger.warn("DB_URL desde properties");
        }
/*
  ESTO SOLO FUNCIONABA EN LA VERSION DONDE SE RECOGIA TODO DE PROPERTIES O DE VARIABLES GLOBALES, AHORA CON LOGIN SE ACCEDE DESDE DATOS DE TABLA USUARIOS
        // USER
        if (System.getenv("DB_USER_escuela") != null) {
            user = System.getenv("DB_USER_escuela");
            logger.info("DB_USER desde ENV");
        } else {
            user = props.getProperty("USUARIO");
            logger.warn("DB_USER desde properties");
        }

        // PASSWORD
        if (System.getenv("DB_PW_escuela") != null) {
            pass = System.getenv("DB_PW_escuela");
            logger.info("DB_USER desde ENV");
        } else {
            pass = props.getProperty("PASSWORD");
            logger.warn("DB_PASSWORD desde properties");
        }
*/
     
        
        //CONFIGURAMOS HIKARI 
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(pass);
        
        dataSource = new HikariDataSource(config);
        Connection con = dataSource.getConnection();
        return con;
        
    }
}