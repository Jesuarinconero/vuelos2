public class Miembro {

    private String dni;
    private String nombre;

    private String categoria;

    private String apellido1;

    private String apellido2;

    private int edad;
    private String telefono;
    private String direccion;
    //private  foto;
    private String correo;
    private String infoAd;





    public Miembro(String dni , String nombre, String categoria ) {
        this.dni = dni;
        this.nombre = nombre;
        this.categoria = categoria;

    }


    public Miembro(String dni, String nombre, String categoria, String apellido1, String apellido2, int edad, String telefono, String direccion, String correo, String infoAd) {
        this.dni = dni;
        this.nombre = nombre;
        this.categoria = categoria;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.edad = edad;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
        this.infoAd = infoAd;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }


    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getInfoAd() {
        return infoAd;
    }

    public void setInfoAd(String infoAd) {
        this.infoAd = infoAd;
    }

    @Override
    public String toString() {
        return  dni +" "+ nombre+ " " + categoria ;
    }

}
