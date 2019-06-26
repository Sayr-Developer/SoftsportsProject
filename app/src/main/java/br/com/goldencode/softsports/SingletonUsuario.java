package br.com.goldencode.softsports;

    public class SingletonUsuario {

        public static SingletonUsuario instance = null;
        public static Usuario usuario = null;
        public static String nome;
        public static String email;
        public static SingletonUsuario getInstance() {
            if (instance == null) {

                usuario = new Usuario(nome,email);
                return instance = new SingletonUsuario();
            } else {
                return instance;
            }
        }

        public void setUsuario(Usuario usuario) {
            SingletonUsuario.usuario = usuario;
        }

        public Usuario getUsuario() {
            return SingletonUsuario.usuario;
        }
    }
