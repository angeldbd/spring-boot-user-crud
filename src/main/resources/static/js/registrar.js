async function registrarUsuario(){

    let datos = {};
    datos.nombre = document.getElementById('txtNombre').value;
    datos.apellido = document.getElementById('txtApellido').value;
    datos.email = document.getElementById('txtEmail').value;
    datos.password = document.getElementById('txtPassword').value;
    let repetirPassword = document.getElementById('txtRepetirPassword').value;

    if(repetirPassword != datos.password){
        alert('La contrasenia que escribiste es diferente.')
        return;
    }

        const request = await fetch('api/crearUsuario', {
        method: 'POST',
        headers:{
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
        });

        alert('La cuenta fue creada con exito!');
        window.location.href = 'usuarios.html'

        if (!request.ok) {
          throw new Error(`Error HTTP: ${request.status}`);
        }

}
