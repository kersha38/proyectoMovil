import {Body, Controller, Post, Res} from "@nestjs/common";
import {RaspeberryService} from "./raspeberry.service";

@Controller('Raspberry')
export class RaspberryController {
    constructor(private _raspberryService:RaspeberryService){

    }

    @Post('actualizarSenso')
    actualizarSenso(@Body('raspberry') raspberry,@Body('estado') estado){
        return this._raspberryService.actualizarSenso(raspberry,estado);
    }

    @Post('obtenerOrden')
    obtenerOrden(@Body('raspberry') raspberry){
        return this._raspberryService.obtenerOrden(raspberry);
    }

    @Post('obtenerRaspberry')
    obtenerRaspberry(@Res() response){
        const raspberry=response.ip;
        response.redirect("http(s)://api.qrserver.com/v1/create-qr-code/?data=raspberry&size=100x100");
    }
}