import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import {UsuarioController} from "./movil/usuario.controller";
import {UsuarioService} from "./movil/usuario.service";
import {RaspeberryService} from "./raspberry/raspeberry.service";
import {RaspberryController} from "./raspberry/raspberry.controller";
import {MovilController} from "./movil/movil.controller";

@Module({
  imports: [],
  controllers: [AppController, UsuarioController,RaspberryController,MovilController],
  providers: [AppService, UsuarioService,RaspeberryService]
})
export class AppModule {}
