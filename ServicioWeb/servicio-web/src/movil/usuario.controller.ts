import {Body, Controller, Get, Post} from "@nestjs/common";
import {UsuarioService} from "./usuario.service";
import {RaspeberryService} from "../raspberry/raspeberry.service";

@Controller('Movil')
export class UsuarioController{
    constructor(private _usuarioService:UsuarioService,
                _raspberryService:RaspeberryService){
    }

    @Post('recibirOrden')
    recibirOrden(){

    }

    @Post('crear')
    crearUsuario(
        @Body('nombre') nombre,
        @Body('correo') correo,
        @Body('password') password
    ){
        return this._usuarioService.añadirUsuario(nombre, correo, password);
    }

    @Get('listar')
    cargarUsuario(){
        return this._usuarioService.listarUsuarios();
    }
}