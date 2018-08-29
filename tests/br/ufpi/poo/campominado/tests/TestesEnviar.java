package br.ufpi.poo.campominado.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

import org.junit.Test;

import br.ufpi.poo.campominado.enums.Acao;
import br.ufpi.poo.campominado.enums.EstadoZona;
import br.ufpi.poo.campominado.exceptions.AcaoInvalidaException;
import br.ufpi.poo.campominado.exceptions.BombaExplodiuException;
import br.ufpi.poo.campominado.exceptions.PosicaoInvalidaException;
import br.ufpi.poo.campominado.model.CampoMinado;
import br.ufpi.poo.campominado.model.Coordenada;
import br.ufpi.poo.campominado.model.Jogada;
import br.ufpi.poo.campominado.model.Tabuleiro;
import br.ufpi.poo.campominado.model.Zona;

public class TestesEnviar {
	
	//Verifica se o tabuleiro está completamente marcado
	@Test
	public void tabuleiroMarcadoTest() throws PosicaoInvalidaException {
		CampoMinado cm = new CampoMinado();
		cm.reseta();
		
		Tabuleiro t = cm.getTabuleiro();

		EstadoZona estado;
		for (int x = 0; x < t.getComprimento(); x++) {
			for (int y = 0; y < t.getLargura(); y++) {
				estado = t.getEstado(new Coordenada(x,y));
				assertFalse(estado.equals(EstadoZona.MARCADO));
							
			}
		}
	}

	
	//Verifica se o tabuleiro está completamente investigado
	@Test
	public void tabuleiroCompletoTest() throws PosicaoInvalidaException {
		CampoMinado cm = new CampoMinado();
		cm.reseta();
		
		Tabuleiro t = cm.getTabuleiro();

		EstadoZona estado;
		for (int x = 0; x < t.getComprimento(); x++) {
			for (int y = 0; y < t.getLargura(); y++) {
				estado = t.getEstado(new Coordenada(x,y));
				assertFalse(estado.equals(EstadoZona.ABERTO));
							
			}
		}
	}
		
	//Impede que seja marcada uma zona que já tenha sido investigada
	
	@Test(expected = PosicaoInvalidaException.class)
	public void investigadoTest() throws PosicaoInvalidaException, AcaoInvalidaException, BombaExplodiuException{
		CampoMinado cm = new CampoMinado();
		cm.reseta();
		Jogada n1 = new Jogada(Acao.INVESTIGAR, new Coordenada(0,0));
		Jogada n2 = new Jogada(Acao.MARCAR, new Coordenada(0,0));
		cm.executa(n1);
		cm.executa(n2);
	}
		
	//impedir que seja realizada ação em zona marcada
	
	@Test
	public void zonaJaMarcadaTest() throws PosicaoInvalidaException{
		CampoMinado cm = new CampoMinado();
		cm.reseta();
		
		Tabuleiro t = cm.getTabuleiro();
		Coordenada umaCoordenada = new Coordenada(0, 0);
		
		EstadoZona estado = t.getEstado(umaCoordenada);
		
		
		assertFalse(estado.equals(EstadoZona.MARCADO));
		
	}
		
	//impedir que seja realizada ação em zona aberta
	
	@Test
	public void zonaJaAbertaTest() throws PosicaoInvalidaException{
		CampoMinado cm = new CampoMinado();
		cm.reseta();
		
		Tabuleiro t = cm.getTabuleiro();
		Coordenada umaCoordenada = new Coordenada(0, 0);
		
		EstadoZona estado = t.getEstado(umaCoordenada);
		
		assertFalse(estado.equals(EstadoZona.ABERTO));
		
	}
	
	//Permitir ação apenas em zona livre
	
	@Test
	public void zonaLivre() throws PosicaoInvalidaException{
		CampoMinado cm = new CampoMinado();
		cm.reseta();
		
		Tabuleiro t = cm.getTabuleiro();
		Coordenada umaCoordenada = new Coordenada(0, 0);
		
		EstadoZona estado = t.getEstado(umaCoordenada);
		
		assertTrue(estado.equals(EstadoZona.VAZIO));
		
	}
	
	//Não permite investigar zona após derrota
	@Test
	public void investigarAposDerrota() throws PosicaoInvalidaException, BombaExplodiuException, AcaoInvalidaException{
		CampoMinado cm = new CampoMinado();
		cm.reseta();
		
		Zona z = new Zona();
		Coordenada umaCoordenada = new Coordenada(0, 0);
		
		Jogada jog = new Jogada(Acao.INVESTIGAR, umaCoordenada);
		cm.executa(jog);
		
		EstadoZona atual = cm.getTabuleiro().getZona(umaCoordenada).getEstadoZona();
		if(z.temBomba()) {
			assertFalse(atual.equals(Acao.INVESTIGAR));
		}
		
	}
	
	//Não permite marcar zona após derrota
	@Test
	public void marcarAposDerrota() throws PosicaoInvalidaException, BombaExplodiuException, AcaoInvalidaException{
		CampoMinado cm = new CampoMinado();
		cm.reseta();
		
		Zona z = new Zona();
		Coordenada umaCoordenada = new Coordenada(0, 0);
		
		Jogada jog = new Jogada(Acao.INVESTIGAR, umaCoordenada);
		cm.executa(jog);
		
		EstadoZona atual = cm.getTabuleiro().getZona(umaCoordenada).getEstadoZona();
		if(z.temBomba()) {
			assertFalse(atual.equals(Acao.INVESTIGAR));
		}
		
	}
	
	
}


