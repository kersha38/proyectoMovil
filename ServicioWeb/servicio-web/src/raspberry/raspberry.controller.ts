import {Body, Controller, Get, Post, Query, Req, Res} from "@nestjs/common";
import {RaspeberryService} from "./raspeberry.service";

@Controller('Raspberry')
export class RaspberryController {
    constructor(private _raspberryService:RaspeberryService){

    }

    @Get('actualizarSenso')
    actualizarSenso(@Query('raspberry') raspberry,@Query('estado') estado){
        return this._raspberryService.actualizarSenso(raspberry,estado);
    }

    @Get('obtenerOrden')
    obtenerOrden(@Query('raspberry') raspberry){
        return this._raspberryService.obtenerOrden(raspberry);
    }

    @Get('obtenerRaspberry')
    obtenerRaspberry(@Req() request,@Res() response){
        const raspberry=request.ip;
        console.log("idRaspberry: "+raspberry)
        response.redirect('http://api.qrserver.com/v1/create-qr-code/?data='+raspberry+'&size=100x100');
    }
}