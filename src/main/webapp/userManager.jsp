<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 5/4/2024
  Time: 12:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://code.jquery.com/jquery-3.7.1.js"
            integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script src="https://cdn.datatables.net/2.0.6/js/dataTables.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.datatables.net/2.0.6/css/dataTables.dataTables.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
          integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>
<body>
<div class="container-fluid">

    <h1 class="text-center">User Manager</h1>
    <a href="addUser.jsp">
        <button class="btn btn-primary">Add User</button>
    </a>
    <a href="LogManager">
        <button class="btn btn-warning">View logs</button>
    </a>
    <table id="user-table">
        <thead>
        <tr>
            <td>Id</td>
            <td>Full Name</td>
            <td>Email</td>
            <td>Password</td>
            <td>Chức năng</td>
        </tr>
        </thead>
    </table>
</div>
</body>
<script>
    function renderTable() {
        let table = new DataTable('#user-table', {
            ajax: "UserManager",
            processing: true,
            serverSide: true,
            columns: [
                {"data": "id"},
                {"data": "fullName"},
                {"data": "email"},
                {"data": "password"},
                {
                    "data": "id",
                    render: (data, type) => `<i data-target=\${data} class="fa-solid fa-trash "></i>
        <a href="AddUser?action=get&id=\${data}"><i class="fa-solid fa-pen-to-square"></i></a>
        `
                }
            ]
        });
    }

    renderTable();

    $(document).on("click", ".fa-trash", (event) => {
        Swal.fire({
            title: "Are you sure?",
            text: "You won't be able to revert this!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes, delete it!"
        }).then((result) => {
            if (result.isConfirmed) {
                let id = $(event.target).data("target");
                $.ajax({
                    url: "Delete",
                    data: {id: id},
                    dataType: 'json',
                    type: "POST",
                    success: res => {
                        console.log(res);
                    }
                })
                $("#user-table").DataTable().clear().destroy();
                renderTable();
                Swal.fire({
                    title: "Deleted!",
                    text: "Your file has been deleted.",
                    icon: "success"
                });
            }
        });


    })
</script>
<style>
    i {
        font-size: 2rem;
        cursor: pointer;
    }

    .fa-pen-to-square {
        color: darkorange;
    }

</style>
</html>
