window.addEventListener('load', function() {
    localStorage.clear();
});

async function registrarUsuario(){

    try{
    // 1. Validar campos vaciós
    let datos = {
        nombre : document.getElementById('txtNombre').value.trim(),
        apellido : document.getElementById('txtApellido').value.trim(),
        email : document.getElementById('txtEmail').value.trim(),
        password : document.getElementById('txtPassword').value.trim()
        };

    let repetirPassword = document.getElementById('txtRepetirPassword').value;


    if(!datos.nombre || !datos.apellido || !datos.email || !datos.password){
        alert('Todos los campos son obligatorios');
        return;
    }

    if(repetirPassword != datos.password){
                alert('La contrasenia que escribiste es diferente.')
                return;
    }

    // Validar formato de email
    function esEmailValido(email){
        return /^[^\s@]+@[^\s@]+\.[^\s@+$]/.test(email);
    }

    // Validar contraseña
    function esPasswordSegura(password){
        return password.length >=2;
    }

    // Usar en la funcion:
    if(!esEmailValido(datos.email)){
        alert('Email inválido');
        return;
    }

    if(!esPasswordSegura(datos.password)){
        alert('La contraseña debe tener al menos 3 caracteres');
        return;
    }
    //2. hacer el request
    const request = await fetch('api/registro', {
            method: 'POST',
            headers:{
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(datos)
    });

    //3. verificar errores http
    if (!request.ok) {
          throw new Error(`Error HTTP: ${request.status}`);
    }

    // 4. obtener y guardar el token
    const token = await request.text();

    if(!token || token === 'Fail'){
        alert('Error al generar sesión');
        return;
    }

    // guardo el token en el localStorage
    localStorage.token = token;
    localStorage.email = datos.email;

    // 5. Redirigir al final
    alert('La cuenta fue creada con exito!');
    window.location.href = 'usuarios.html'

    } catch (error){
    console.error('Error:', error);
    alert('Error de conexión. Verifica tu internet.');
    }
}
