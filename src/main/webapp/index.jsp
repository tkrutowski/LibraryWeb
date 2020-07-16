<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Library Viewer</title>
</head>
<body>
<h1>Biblioteka viewer</h1>
<form action="Author" method="get">
    <input placeholder="id autora" type="text" name="id">
    <br>
    <input placeholder="Tytuł" type="text" name="title">
    <br>
    <input placeholder="Opis" type="text" name="description">
    <br>
    Szukaj: <input type="radio" name="operation" value="read_one"><br>
    Dodaj: <input type="radio" name="operation" value="add"><br>
    Modyfikuj: <input type="radio" name="operation" value="update"><br>
    Usuń: <input type="radio" name="operation" value="delete"><br>
    <br>
    <input type="submit" value="Wyślij">
</form>

</body>
</html>