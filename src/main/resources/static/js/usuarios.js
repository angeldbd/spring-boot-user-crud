// Call the dataTables jQuery plugin
$(document).ready(function() {
  //cargarUsuarios();
  $('#usuarios').DataTable({
  ajax:{ url: 'api/usuarios',
        dataSrc: ''},
  columns:[
  {data: 'id'},
  {data: 'nombre'},
  {data: 'email'},
  {data: 'telefono'},
  {data: 'id',
  render: function(id){
    return `<a href="#" class="btn btn-danger btnEliminar btn-circle btn-sm" data-id="${id}">
            <i class="fas fa-trash"></i></a> `
  }}
  ]
  });
});

$('#usuarios').on('click', '.btnEliminar', function () {
  const id = $(this).data('id');

  fetch(`api/eliminar/${id}`, {
    method: 'DELETE'
  }).then(() => {
    $('#usuarios').DataTable().ajax.reload();
  });
});

async function cargarUsuarios(){

    let listaUsuarios;
      try {

        const request = await fetch('api/usuarios', {
        method: 'GET',
        headers:{
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
        });

        if (!request.ok) {
          throw new Error(`Error HTTP: ${request.status}`);
        }
        listaUsuarios = await request.json();

      } catch (error) {
        console.error('Hubo un problema:', error);
      }

    let listadoHtml = '';

    for (let usuario of listaUsuarios){
    let botonEliminar =  '<a href="#" onclick= "eleiminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>'

    let telefono = usuario.telefono == null ? '-' : usuario.telefono;
    let  usuarioHtml= '<tr><td>'+usuario.id+'</td><td>'+usuario.nombre+
    ' ' +usuario.apellido+'</td><td>'+usuario.email+
    '</td><td>'+usuario.telefono+'</td><td>'
   +botonEliminar+'</td></tr>';
    listadoHtml += usuarioHtml;
    }

    document.querySelector('#usuarios tbody').outerHTML = listadoHtml;

}

async function eliminarUsuario(id){
    if(!confirm('¿Desea eliminar este usuario? ')){
            return;
        }
    const request = await fetch('api/usuarios/' + id, {
            method: 'DELETE',
            headers:{
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            }
            });

            if (!request.ok) {
              throw new Error(`Error HTTP: ${request.status}`);
            }

            location.reload();

}