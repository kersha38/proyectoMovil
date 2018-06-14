import {Controller, Post} from "@nestjs/common";
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

}