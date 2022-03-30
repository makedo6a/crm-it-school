package kg.itschool.dao.daoutil;


import kg.itschool.dao.ManagerDao;
import kg.itschool.dao.impl.ManagerDaoImpl;

public abstract class DaoFactory {

    static {
        try {
            System.out.println("Loading driver...");
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver loaded.");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver loading failed");
            e.printStackTrace();
        }
    }

    public static ManagerDao getManagerDaoSql() {
        return new ManagerDaoImpl();

    }
}
