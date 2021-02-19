package es.udc.ws.runfic.thriftservice;

import es.udc.ws.runfic.thrift.ThriftCarreraService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServlet;

public class ThriftCarreraServiceServlet extends TServlet {

    public ThriftCarreraServiceServlet() {
        super(createProcessor(), createProtocolFactory());
    }

    private static TProcessor createProcessor() {

        return new ThriftCarreraService.Processor<ThriftCarreraService.Iface>(
            new ThriftCarreraServiceImpl());

    }

    private static TProtocolFactory createProtocolFactory() {
        return new TBinaryProtocol.Factory();
    }

}
