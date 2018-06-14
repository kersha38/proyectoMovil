import {Injectable} from "@nestjs/common";

const fs = require('fs');

@Injectable()
export class UsuarioService {
    listaUsuarios: Usuario[] = [];

    listarUsuarios() {
        let users = []
        users = JSON.parse(fs.readFileSync(__dirname + '/archivos/usuarios.json', 'utf8'));
        //cargar usuarios desde un archivo
        users.forEach(usuario => this.listaUsuarios.push(usuario));
        console.log(users);
        console.log(this.listaUsuarios);

    }

    añadirUsuario(nombre, correo, password) {
        const usuario = new Usuario(nombre, password, "", correo);
        this.listaUsuarios.push(usuario);
        // escribir usuario en file
        fs.writeFile(
            __dirname + '/archivos/usuarios.json',
            JSON.stringify(this.listarUsuarios(), null, 4), (err) => {
                if (err) {
                    console.error(err);
                    return;
                }
                console.log("JsonUsuarios creado");
            });
        return this.listarUsuarios();
    }

    añadirUsuarioGmail(nombre, correo) {
        const usuario = new Usuario(nombre, "", "", correo);
        this.listaUsuarios.push(usuario);
        // escribir usuario en file
    }

    asignarQR(usuario, mac) {
        //this.listaUsuarios.find();
        //encuentro usuario, cambio su mac y guardo

    }

    verificarUsuario(nombre, password): boolean {
        //verifico existe usuario con nombre y password
        return false
    }

    verificarUsuarioGmail(nombre, gmail): boolean {
        //lo mismo
        return false

    }

}

export class Usuario {
    constructor(public nombre: string,
                public password: string,
                public mac: string,
                public correo: string) {
    }
}