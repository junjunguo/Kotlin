interface IEnviroment {
  baseUrl: string;
}

class EnvProd implements IEnviroment {
  readonly baseUrl = "https://";
}

class EnvDev implements IEnviroment {
  readonly baseUrl = "http://localhost:8080/";
}

export class Enviroment extends EnvDev {}
