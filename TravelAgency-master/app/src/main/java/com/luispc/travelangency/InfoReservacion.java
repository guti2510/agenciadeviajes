package com.luispc.travelangency;

/**
 * Created by LuisPC on 4/20/15.
 */
public class InfoReservacion {

    private String _Nombre;
    private String _CantidadPersonas;
    private String _DiaReservado;
    private String _FechaFinal;
    private String _PrecioFinal;
    private String _TipoReserva;
    private String _CorreoUsuario;
    private String _Oferta;

    public InfoReservacion(){}

    public InfoReservacion(String Nombre, String CantidadPersonas, String DiaReservado,
                           String FechaFinal, String PrecioFinal, String TipoReserva, String CorreoUsuario,
                           String Oferta) {
        this._Nombre = Nombre;
        this._CantidadPersonas = CantidadPersonas;
        this._DiaReservado = DiaReservado;
        this._FechaFinal = FechaFinal;
        this._PrecioFinal = PrecioFinal;
        this._TipoReserva = TipoReserva;
        this._CorreoUsuario = CorreoUsuario;
        this._Oferta = Oferta;
    }

    public String getNombre() {
        return _Nombre;
    }

    public String getCantidadPersonas() {
        return _CantidadPersonas;
    }

    public String getDiaReservado() {
        return _DiaReservado;
    }

    public String getFechaFinal() {
        return _FechaFinal;
    }

    public String getPrecioFinal() {
        return _PrecioFinal;
    }

    public String getTipoReserva() {
        return _TipoReserva;
    }

    public String getCorreoUsuario() {
        return _CorreoUsuario;
    }

    public String getOferta() {
        return _Oferta;
    }
}