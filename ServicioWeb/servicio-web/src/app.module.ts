import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import {UsuarioController} from "./movil/usuario.controller";
import {UsuarioService} from "./movil/usuario.service";
import {RaspeberryService} from "./raspberry/raspeberry.service";

@Module({
  imports: [],
  controllers: [AppController, UsuarioController],
  providers: [AppService, UsuarioService,RaspeberryService]
})
export class AppModule {}
