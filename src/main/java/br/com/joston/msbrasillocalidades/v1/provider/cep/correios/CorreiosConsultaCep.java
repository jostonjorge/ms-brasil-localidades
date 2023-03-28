package br.com.joston.msbrasillocalidades.v1.provider.cep.correios;

import br.com.joston.msbrasillocalidades.v1.dto.EnderecoDto;
import br.com.joston.msbrasillocalidades.v1.provider.cep.ConsultaCep;
import br.com.joston.msbrasillocalidades.v1.provider.correios.wsdl.ConsultaCEP;
import br.com.joston.msbrasillocalidades.v1.provider.correios.wsdl.ConsultaCEPResponse;
import br.com.joston.msbrasillocalidades.v1.provider.correios.wsdl.EnderecoERP;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

@Component("CorreiosConsultaCepV1")
public class CorreiosConsultaCep extends WebServiceGatewaySupport implements ConsultaCep {
    private final static String URI = "https://apps.correios.com.br/SigepMasterJPA/AtendeClienteService/AtendeCliente?wsdl";

    public CorreiosConsultaCep(){
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("br.com.joston.msbrasillocalidades.v1.provider.correios.wsdl");
        this.setDefaultUri(URI);
        this.setMarshaller(marshaller);
        this.setUnmarshaller(marshaller);
    }

    @Override
    public EnderecoDto execute(String cep) {
        ConsultaCEPResponse response = request(cep);
        return parseResponse(response);
    }
    private EnderecoDto parseResponse(ConsultaCEPResponse response){
        EnderecoERP enderecoERP = response.getReturn();
        return EnderecoDto.builder()
                .logradouro(enderecoERP.getEnd())
                .bairro(enderecoERP.getBairro())
                .cidade(enderecoERP.getCidade())
                .uf(enderecoERP.getUf())
                .cep(enderecoERP.getCep())
                .build();
    }
    public ConsultaCEPResponse request(String cep) {
        ConsultaCEP request = new ConsultaCEP();
        request.setCep(cep);
        JAXBElement<ConsultaCEP> xmlRequest = new JAXBElement<>(new QName("http://cliente.bean.master.sigep.bsb.correios.com.br/", "consultaCEP"),ConsultaCEP.class,request);
        @SuppressWarnings("unchecked")
        JAXBElement<ConsultaCEPResponse> response = (JAXBElement<ConsultaCEPResponse>) getWebServiceTemplate().marshalSendAndReceive(URI,xmlRequest);
        return response.getValue();
    }
}
