package es.udc.ws.runfic.model.inscripcion;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDateTime;

public class Inscripcion {
    private Long inscripcionId;
    private String emailUsuario;
    private Long carrera;
    private String tarjeta;
    private int dorsal;
    private LocalDateTime fechaInscripcion;
    private boolean entregado = false;

    public Inscripcion(String emailUsuario, Long carrera, String tarjeta,int dorsal,LocalDateTime fechaInscripcion) {
        this.emailUsuario = emailUsuario;
        this.carrera = carrera;
        this.tarjeta = tarjeta;
        this.dorsal=dorsal;
        this.fechaInscripcion=fechaInscripcion;
    }

    public Inscripcion(String emailUsuario, Long carrera, String tarjeta,int dorsal,LocalDateTime fechaInscripcion, boolean entregado) {
        this.emailUsuario = emailUsuario;
        this.carrera = carrera;
        this.tarjeta = tarjeta;
        this.dorsal=dorsal;
        this.fechaInscripcion=fechaInscripcion;
        this.entregado=entregado;
    }

    public Inscripcion(Long inscripcionId,String emailUsuario, Long carrera, String tarjeta,int dorsal,LocalDateTime fechaInscripcion) {
        this(emailUsuario,carrera,tarjeta,dorsal,fechaInscripcion);
        this.inscripcionId=inscripcionId;
    }

    public Inscripcion(Long inscripcionId,String emailUsuario, Long carrera, String tarjeta,int dorsal,LocalDateTime fechaInscripcion,boolean entregado) {
        this(emailUsuario,carrera,tarjeta,dorsal,fechaInscripcion,entregado);
        this.inscripcionId=inscripcionId;

    }

    public String getEmailUsuario() {return emailUsuario;}

    public void setEmailUsuario(String emailUsuario) {this.emailUsuario = emailUsuario;}

    public Long getCarrera() {return carrera;}

    public void setCarrera(Long carrera) {this.carrera = carrera;}

    public String getTarjeta() {return tarjeta;}

    public void setTarjeta(String tarjeta) {this.tarjeta = tarjeta;}

    public int getDorsal() {return dorsal;}

    public void setDorsal(int dorsal) {this.dorsal = dorsal;}

    public LocalDateTime getFechaInscripcion() {return fechaInscripcion;}

    public void setFechaInscripcion(LocalDateTime fechaInscripcion) {this.fechaInscripcion = (fechaInscripcion != null) ? fechaInscripcion.withNano(0) : null;}

    public boolean getEntregado() { return entregado; }

    public void setEntregado(boolean entregado) { this.entregado = entregado; }

    public Long getInscripcionId() {return inscripcionId;}

    public void setInscripcionId(Long inscripcionId) { this.inscripcionId = inscripcionId;}

    @Override
    public int hashCode() {
        final int prime = 43;
        int result = 1;
        result = prime * result + ((tarjeta == null) ? 0 : tarjeta.hashCode());
        result = prime * result + ((emailUsuario == null) ? 0 : emailUsuario.hashCode());
        result = prime * result + ((fechaInscripcion == null) ? 0 : fechaInscripcion.hashCode());
        result = prime * result + ((carrera == null) ? 0 : carrera.hashCode());
        result = prime * result + dorsal;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Inscripcion tmp = (Inscripcion) obj;
        if (tarjeta == null) {
            if (tmp.tarjeta != null)
                return false;
        } else if (!tarjeta.equals(tmp.tarjeta))
            return false;
        if (fechaInscripcion == null) {
            if (tmp.fechaInscripcion != null)
                return false;
        } else if (!fechaInscripcion.equals(tmp.fechaInscripcion))
            return false;
        if (carrera == null) {
            if (tmp.carrera != null)
                return false;
        } else if (!carrera.equals(tmp.carrera))
            return false;
        if (dorsal != tmp.dorsal)
            return false;
        if (emailUsuario == null) {
            if (tmp.emailUsuario != null)
                return false;
        } else if (!emailUsuario.equals(tmp.emailUsuario))
            return false;
        return true;
    }
}
