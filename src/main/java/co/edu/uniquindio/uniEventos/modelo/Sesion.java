package co.edu.uniquindio.uniEventos.modelo;

public class Sesion {
        public static Sesion INSTANCIA;
        private Usuario usuario;

        public static Sesion getInstancia(){
            if (INSTANCIA==null){
                INSTANCIA = new Sesion();
            }
            return INSTANCIA;
        }

        public void cerrarSesion(){
            usuario = null;
        }

}
