package br.com.fernandoaag.agendaapp.rest;

import java.util.List;

import br.com.fernandoaag.agendaapp.model.Contatos;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface ApiInterface {
    @GET("listaContatos")
    Call<List<Contatos>> listaContatos();

    @GET("getContatoNome/{nome}")
    Call<List<Contatos>> listaContatoNome(@Path("nome") String nome);

    @POST("cadastrar")
    Call<Contatos> criaContato(@Body Contatos contatos);

    @PUT("alterar")
    Call<Contatos> alteraContato(@Body Contatos contatos);

    @DELETE("excluir/{idContato}")
    Call<Contatos> delContato(@Path("idContato") int idContato);
}
