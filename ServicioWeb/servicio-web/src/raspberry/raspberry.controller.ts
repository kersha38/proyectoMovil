import {Body, Controller, Post, Req, Res} from "@nestjs/common";
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
    obtenerRaspberry(@Req() request,@Res() response){
        const raspberry=request.ip;
        console.log("idRaspberry: "+raspberry)
        response.redirect('http://api.qrserver.com/v1/create-qr-code/?data='+raspberry+'&size=100x100');
    }
}