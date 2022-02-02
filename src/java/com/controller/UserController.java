/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.model.ModelCasauser;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Mbah Royce
 */
@Path("usercontroller")
public class UserController {

    @GET
    @Path("/casauser")
    @Produces({"application/json"})
    public ArrayList<ModelCasauser> listuser() throws Exception {
        ArrayList<ModelCasauser> listu = new ArrayList<>();
        try {
            Statement etat = com.connexion.Connexion.seconnecter().createStatement();
            ResultSet rs = etat.executeQuery("select * from casauser order by iduser");
            while (rs.next()) {
                ModelCasauser oneuser = new ModelCasauser();
                oneuser.setIduser(rs.getInt("iduser"));
                oneuser.setName(rs.getString("name"));
                oneuser.setEmail(rs.getString("email"));
                oneuser.setPhonenumber(rs.getString("phone"));
                oneuser.setLogin(rs.getString("login"));
                oneuser.setPwd(rs.getString("pwd"));
                oneuser.setDob(rs.getTimestamp("dob"));
                oneuser.setCity(rs.getString("city"));
                oneuser.setCountry(rs.getString("country"));
                oneuser.setStatus(rs.getInt("status"));

                listu.add(oneuser);
            }
            etat.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("error loading data :" + e.getMessage());
        }
        return listu;
    }

    @POST
    @Path("/casauser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"application/json"})
    public ModelCasauser createUser(ModelCasauser user) {
        try {
            String sqlStatment = "Insert into casauser (name,email,phone,login,pwd,dob,city,country,status) " + "values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = com.connexion.Connexion.seconnecter().prepareStatement(sqlStatment);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPhonenumber());
            pstmt.setString(4, user.getLogin());
            pstmt.setString(5, user.getPwd());
            pstmt.setTimestamp(6, user.getDob());
            pstmt.setString(7, user.getCity());
            pstmt.setString(8, user.getCountry());
            pstmt.setInt(9, user.getStatus());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("error in querry" + e.getMessage());
        }
        return user;
    }

    @PUT
    @Path("/casauser/{userid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"application/json"})
    public ModelCasauser updateUser(@PathParam("userid") int userid, ModelCasauser user) {
        try {
            String sqlStatment = "update casauser set name=?,email=?,phone=?,login=?,pwd=?,dob=?,city=?,country=?,status=? where iduser =" + userid;
            PreparedStatement pstmt = com.connexion.Connexion.seconnecter().prepareStatement(sqlStatment);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPhonenumber());
            pstmt.setString(4, user.getLogin());
            pstmt.setString(5, user.getPwd());
            pstmt.setTimestamp(6, user.getDob());
            pstmt.setString(7, user.getCity());
            pstmt.setString(8, user.getCountry());
            pstmt.setInt(9, user.getStatus());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("error in querry" + e.getMessage());
        }
        return user;
    }

    @DELETE
    @Path("/casauser/{userid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"application/json"})
    public String deleteUser(@PathParam("userid") int userid) {
        try {
            String sqlStatment = "delete from casauser where iduser =" + userid;
            PreparedStatement pstmt = com.connexion.Connexion.seconnecter().prepareStatement(sqlStatment);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("error in querry" + e.getMessage());
        }
        return "Deleted Successfully";
    }
}
