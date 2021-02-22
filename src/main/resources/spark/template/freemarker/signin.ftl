<!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
          <meta http-equiv="refresh" content="10">
          <title>Sign-In | ${title}</title>
        <link rel="stylesheet" type="text/css" href="/css/style.css">
    </head>

    <body>



        <div class = "page">


            <h1>Sign-In | ${title}</h1>

            <div class = "body">
                <#include "message.ftl" />
            </div>

            <form action="/" method="POST">
                <label for="fname">Name:</label>
                <br>
                <input type="text" id="fname" name="fname">
                <br>
                <button type="submit">Submit</button><br>
            </form>


        </div>
    </body>
</html>