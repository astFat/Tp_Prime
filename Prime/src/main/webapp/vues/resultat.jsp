<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Resultat du calcul</title>
</head>
<body>
  <h2 align="center">Bienvenue ${pm.nom} ${pm.prenom}</h2>
  <p align="center">La prime :<font color="blue">${pm.prime} </font> </p>
  <a href="<%=request.getContextPath()%>/acceuil.php">Retour</a>
</body>
</html>