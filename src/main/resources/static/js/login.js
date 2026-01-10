

async function iniciarSesion(){

    let datos = {};
    datos.email = document.getElementById('txtEmail').value;
    datos.password = document.getElementById('txtPassword').value;

        const request = await fetch('api/login', {
        method: 'POST',
        headers:{
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
        });

        if (!request.ok) {
          throw new Error(`Error HTTP: ${request.status}`);
        }

        const response = await request.json();

}
