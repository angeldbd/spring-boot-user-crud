

async function iniciarSesion(){
    try{
    let datos = {
    email:document.getElementById('txtEmail').value,
    password:document.getElementById('txtPassword').value
    };

    if(!datos.email || !datos.password){
        alert('Completa todos los campos');
        return;
    }
        const request = await fetch('api/login', {
        method: 'POST',
        headers:{
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
        });

        const respuesta = await request.text();

        if(request.ok){

        localStorage.token = respuesta;
        localStorage.email =  datos.email;
        window.location.href = 'usuarios.html';
        } else if(request.status === 401){
         alert('las credenciales son incorrectas');
         return;
        } else {
        alert('Error del servidor, Intenta nuevamente.')
        return;
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Error de conexión');
    }

}
