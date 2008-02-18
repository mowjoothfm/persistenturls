package org.purl.accessor.util;

import org.ten60.netkernel.layer1.nkf.INKFConvenienceHelper;
import org.ten60.netkernel.layer1.nkf.INKFRequest;
import org.ten60.netkernel.layer1.nkf.NKFException;
import org.ten60.netkernel.xml.representation.IAspectXDA;

import com.ten60.netkernel.urii.IURAspect;
import com.ten60.netkernel.urii.IURRepresentation;
import com.ten60.netkernel.urii.aspect.IAspectBoolean;

public class PURLResourceStorage implements ResourceStorage {

    public IURAspect getResource(INKFConvenienceHelper context, URIResolver resolver) throws NKFException {
        return getResource(context, resolver.getURI(context));
    }

    public IURAspect getResource(INKFConvenienceHelper context, String uri) throws NKFException {
        INKFRequest req = context.createSubRequest("active:purl-storage-query-purl");
        req.addArgument("uri", uri);
        IURRepresentation res = context.issueSubRequest(req);
        return context.transrept(res, IAspectXDA.class);
    }

    public boolean resourceExists(INKFConvenienceHelper context, String uri) throws NKFException {
        boolean retValue = false;
        INKFRequest req = context.createSubRequest("active:purl-storage-purl-exists");
        req.addArgument("uri", uri);
        req.setAspectClass(IAspectBoolean.class);
        retValue = ((IAspectBoolean) context.issueSubRequestForAspect(req)).isTrue();
        return retValue;
    }

    public boolean resourceExists(INKFConvenienceHelper context, URIResolver resolver) throws NKFException {
        return resourceExists(context, resolver.getURI(context));
    }

    public IURAspect storeResource(INKFConvenienceHelper context, URIResolver resolver, IURAspect resource) throws NKFException {
        IURAspect retValue = null;
        INKFRequest req = context.createSubRequest("active:purl-storage-create-purl");
        req.addArgument("param", resource);
        req.setAspectClass(IAspectXDA.class);
        retValue = context.issueSubRequestForAspect(req);
        
        updatePURLHistory(context, resolver, "data:text/plain,0");
        return retValue;
    }
    
    public boolean updateResource(INKFConvenienceHelper context, URIResolver resolver, IURAspect resource) throws NKFException {
        boolean retValue = false;
        
        INKFRequest req = context.createSubRequest("active:purl-storage-update-purl");
        req.addArgument("param", resource);
        context.issueSubRequest(req);
        updatePURLHistory(context, resolver, "data:text/plain,1");        
        retValue = true;
        
        return retValue;
    }

    public boolean deleteResource(INKFConvenienceHelper context, String uri) throws NKFException {
        return false;
    }

    public boolean deleteResource(INKFConvenienceHelper context, URIResolver resolver) throws NKFException {
        boolean retValue = false;
        INKFRequest req = context.createSubRequest("active:purl-storage-delete-purl");
        req.addArgument("uri", resolver.getURI(context));
        context.issueSubRequest(req);
        updatePURLHistory(context, resolver, "data:text/plain,2");
        retValue = true;
        return retValue;
    }
    
    protected void updatePURLHistory(INKFConvenienceHelper context, URIResolver resolver, String status) throws NKFException {
        INKFRequest req = context.createSubRequest("active:purl-storage-update-purl-history");
        req.addArgument("purl", resolver.getURI(context));
        req.addArgument("user", "data:text/plain," + NKHelper.getUser(context));
        req.addArgument("status", status);
        context.issueSubRequest(req);
    }
}
