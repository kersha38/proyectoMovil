import {Injectable} from "@nestjs/common";

const fs = require('fs');

@Injectable()
export class UsuarioService {
    private users: any[];

    obtenerUsuario(nickname){
        console.log("nickBusqueda: "+nickname);
        var usuarioBuscado:any;
        this.users.forEach((usuario)=>{
            console.log(usuario.nickname)
            if (usuario.nickname == nickname){

                usuarioBuscado=usuario;

            }
        });
        return usuarioBuscado;
    }

    listarUsuarios() {
         this.users = JSON.parse(fs.readFileSync(__dirname + '/archivosDatos/usuarios.json', 'utf8'));
        //cargar usuarios desde un archivo
        //users.forEach(usuario => this.listaUsuarios.push(usuario));
        console.log(this.users);
        //console.log(this.listaUsuarios);
        return this.users;

    }

    crearUsuarioComun(nickname, correo, password) {
        const usuario={"nickname":nickname,"mail":correo,"password":password,"necesitaPassword":"si","ipRasp":""};
        this.users.push(usuario);
        fs.writeFile(
            __dirname + '/archivosDatos/usuarios.json',
            JSON.stringify(this.users, null, 4), (err) => {
                if (err) {
                    console.error(err);
                    return;
                }
                console.log("JsonUsuarios creado");
            });
        return this.users;
    }

    crearConGmailFb(nickname, correo) {
        const usuario={"nickname":nickname,"mail":correo,"password":"","necesitaPassword":"no","ipRasp":""};
        this.users.push(usuario);
        fs.writeFile(
            __dirname + '/archivosDatos/usuarios.json',
            JSON.stringify(this.users, null, 4), (err) => {
                if (err) {
                    console.error(err);
                    return;
                }
                console.log("JsonUsuarios creado");
            });
        return this.users;
    }

    autentificarComun(nickname,password){
        //devuelve undefined si no encuentra
        var usuarioBuscado:any;
        this.users.forEach((usuario)=>{
            console.log(usuario.nickname)
            if (usuario.nickname == nickname && usuario.password==password && usuario.necesitaPassword=="si"){

                usuarioBuscado=usuario;
            }
        });
        return usuarioBuscado;
    }

    autentificarGmailFb(mail){
        var usuarioBuscado:any;
        this.users.forEach((usuario)=>{
            console.log(usuario.nickname)
            if (usuario.mail == mail && usuario.necesitaPassword=="no"){

                usuarioBuscado=usuario;
            }
        });
        return usuarioBuscado;
    }
}
