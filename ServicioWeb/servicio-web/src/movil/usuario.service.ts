import {Injectable} from "@nestjs/common";

const fs = require('fs');

@Injectable()
export class UsuarioService {
    private users: any[];

    obtenerUsuario(nickname){
        console.log("nickBusqueda: "+nickname);
        var usuarioBuscado:any;
        this.users.forEach((usuario)=>{
            //console.log(usuario.nickname)
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

    crearUsuarioComun(nickname, mail, password) {
        var mailDisponible= true;
        this.users.forEach((usuario)=>{
            if(usuario.mail == mail && usuario.necesitaPassword == "si"){
                mailDisponible=false;
            }
        });
        if(mailDisponible){
            const usuario={"nickname":nickname,"mail":mail,"password":password,"necesitaPassword":"si","ipRasp":""};
            this.users.push(usuario);
            fs.writeFile(
                __dirname + '/archivosDatos/usuarios.json',
                JSON.stringify(this.users, null, 4), (err) => {
                    if (err) {
                        console.error(err);
                        return;
                    }
                    console.log("JsonUsuario creado");
                });
            return {"mensaje":"usuarioCreado"};
        }else{
            return {"mensaje":"Email ya fue ocupado"}
        }
    }

    existeGmailFb(mail){
        var existeCuenta= false;
        this.users.forEach((usuario)=>{
            if(usuario.mail == mail){
                existeCuenta=true;
            }
        });
        console.log(existeCuenta);
        return {"mensaje":String(existeCuenta)};
    }

    crearConGmailFb(nickname, mail) {
        const usuario={"nickname":nickname,"mail":mail,"password":"","necesitaPassword":"no","ipRasp":""};
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
        return {"mensaje":"usuarioCreado"};
    }

    autentificarComun(nickname,password){
        //devuelve undefined si no encuentra
        var usuarioBuscado:any;
        this.users.forEach((usuario)=>{
            //console.log(usuario.nickname)
            if (usuario.nickname == nickname && usuario.password==password && usuario.necesitaPassword=="si"){

                usuarioBuscado=usuario;
            }
        });
        return usuarioBuscado;
    }

    autentificarGmailFb(mail){
        var usuarioBuscado:any;
        this.users.forEach((usuario)=>{
            //console.log(usuario.nickname)
            if (usuario.mail == mail && usuario.necesitaPassword=="no"){

                usuarioBuscado=usuario;
            }
        });
        return usuarioBuscado;
    }

    registrarRaspberry(raspberry,mail){
        var usuarioBuscado:any;
        this.users.forEach((usuario,numeroUsuario)=>{
            //console.log(usuario.nickname)
            if (usuario.mail == mail){

                usuarioBuscado=usuario;
                usuarioBuscado.ipRasp=raspberry;
                this.users[numeroUsuario]=usuarioBuscado;
            }
        });
        return usuarioBuscado;

    }
}
