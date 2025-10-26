package com.estn.controleur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;

import com.estn.model.PrimeModel;

@WebServlet(urlPatterns = {"/acceuil.php", "/calculer.php"})
public class Manage_Prime_Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Manage_Prime_Servlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        System.out.print(path);

        if ("/acceuil.php".equals(path)) {
            request.getRequestDispatcher("/vues/index.html").forward(request, response);
        } 
        else if ("/calculer.php".equals(path)) {
            int id = Integer.parseInt(request.getParameter("id_employer"));
            String url = "jdbc:mysql://127.0.0.1:3306/prime";
            String user = "root";
            String password = "root_pwd";
            String nom = "";
            String prenom = "";
            Date date_emb = null;
            double salaire = 0;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, password);

                String sql = "SELECT * FROM employer WHERE id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    nom = rs.getString("nom");
                    prenom = rs.getString("prenom");
                    salaire = rs.getDouble("salaire");
                    date_emb = rs.getDate("date_emb");
                }

                rs.close();
                ps.close();
                con.close();

                if (date_emb != null) {
                    Period p = Period.between(date_emb.toLocalDate(), LocalDate.now());
                    double prime = salaire + (p.getYears() * 500);

                    PrimeModel primeModel = new PrimeModel();
                    primeModel.setNom(nom);
                    primeModel.setPrenom(prenom);
                    primeModel.setPrime(prime);

                    request.setAttribute("primeModel", primeModel);
                    request.getRequestDispatcher("/vues/resultat_prime.jsp").forward(request, response);
                } else {
                    response.getWriter().println("Aucun employé trouvé avec cet ID.");
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        } 
        else {
            request.getRequestDispatcher("/vues/404.html").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
