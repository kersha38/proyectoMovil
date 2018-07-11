import {Injectable} from "@nestjs/common";

const fs = require('fs');

@Injectable()
export class UsuarioService {
    private users: any[];

    obtenerUsuario(nickname){
        console.log("nickBusqueda: "+nickname);
        let usuarioBuscado:any;
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
        let mailDisponible= true;
        this.users.forEach((usuario)=>{
            if(usuario.mail == mail && usuario.necesitaPassword == "si"){
                mailDisponible=false;
            }
        });
        if(mailDisponible){
            const usuario={"nickname":nickname,"mail":mail,"password":password,"necesitaPassword":"si","ipRasp":""};
            this.users.push(usuario);
            this.escribirArchivoUsuarios();
            return {"mensaje":"usuarioCreado"};
        }else{
            return {"mensaje":"Email ya fue ocupado"}
        }
    }

    existeGmailFb(mail){
        let existeCuenta= false;
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
        this.escribirArchivoUsuarios();
        return {"mensaje":"usuarioCreado"};
    }

    autentificarComun(nickname,password){
        //devuelve undefined si no encuentra
        let usuarioBuscado:any;
        this.users.forEach((usuario)=>{
            //console.log(usuario.nickname)
            if (usuario.nickname == nickname && usuario.password==password && usuario.necesitaPassword=="si"){
                usuarioBuscado=usuario;
            }
        });
        return usuarioBuscado;
    }

    autentificarGmailFb(mail){
        let usuarioBuscado:any;
        this.users.forEach((usuario)=>{
            //console.log(usuario.nickname)
            if (usuario.mail == mail && usuario.necesitaPassword=="no"){
                usuarioBuscado=usuario;
                console.log(usuarioBuscado);
            }
        });
        return usuarioBuscado;
    }

    registrarRaspberry(ipRasp,mail){
        let usuarioBuscado:any;
        console.log("raspA",ipRasp);
        this.users.forEach((usuario,numeroUsuario)=>{
            console.log("raspB",usuario.ipRasp);
            if (usuario.mail == mail){

                usuarioBuscado=usuario;
                usuarioBuscado.ipRasp=ipRasp;
                this.users[numeroUsuario]=usuarioBuscado;
                console.log(this.users[numeroUsuario]);
            }
        });

        this.escribirArchivoUsuarios();
        return usuarioBuscado;

    }

    escribirArchivoUsuarios(){
        fs.writeFile(
            __dirname + '/archivosDatos/usuarios.json',
            JSON.stringify(this.users, null, 4), (err) => {
                if (err) {
                    console.error(err);
                    return;
                }
                console.log("JsonUsuario creado");
            });
    }
}
