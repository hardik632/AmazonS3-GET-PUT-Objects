# AmazonS3-GET-PUT-Objects

a java application for getting and putting object from amazon s3

# Requirements

1. java jdk
2. ide(eclipse,vscode etc)

# How to Build and Excute

   download/clone the repository, extract it, open the folder in eclipse/vscode  
   the run the Main.java class of main package
   
   compile and excute the Main class of main pacakage
   
   enter the method you want to perform get(for getting object from the bucket) and put(for putting new object to the bucket)
   
   and enter your object key , bucket name , region , secret_access_key , access_key as input
  

# How it works

When you send HTTP requests to AWS, you sign the requests so that AWS can identify who sent them. 
You sign requests with your AWS access key, which consists of an access key ID and secret access key.

to calculate the signature

1.  canonical request is a string that includes information from your request in a standardized (canonical) format.
    
  Example Canonical request pseudocode
  
    CanonicalRequest =
    HTTPRequestMethod (//get,put,post,etc.) + '\n' + 
    
    CanonicalURI (//The canonical URI is the URI-encoded version of the absolute path component of the URI) + '\n' +  
    
    CanonicalQueryString (//if the request does not include a query string, use an empty string (essentially, a blank line))+ '\n' +
    
    CanonicalHeaders (//The canonical headers consist of a list of all the HTTP headers that you are including with the signed request) + '\n' + 
    
    SignedHeaders (//this value is the list of headers that you included in the canonical headers. By adding this list of headers, you tell AWS which headers in the request are part of the signing process)+ '\n' +
    
    HexEncode(Hash(RequestPayload))(//this value is the list of headers that you included in the canonical headers. By adding this list of headers, you tell AWS which headers in the request are part of the signing process)

  2. The string to sign includes meta information about your request and about the canonical request .
      
   Structure of StringToSign
     
    StringToSign =
    Algorithm (//This value is the hashing algorithm that you use to calculate the digests in the canonical request.For SHA256, AWS4-HMAC-SHA256 is the algorithm.)+ \n +
    
    RequestDateTime (//The date is specified with ISO8601 basic format in the x-amz-date header in the format YYYYMMDD'T'HHMMSS'Z')+ \n +
    
    CredentialScope (//This value is a string that includes the date,the Region you are targeting, the service you are requesting, and a termination string ("aws4_request") in lowercase characters)+ \n +
    
    HashedCanonicalRequest (//the hash of the canonical request that you created in canonicalRequest class )
    
  3. calculating signature for signing http request
  
     Before you calculate a signature, you derive a signing key from your AWS secret access key. Because the derived signing key is specific to the date, service, and Region, it offers a greater degree of protection. You don't just use your secret access key to sign the request. You then use the signing key and the string to sign as the inputs to a keyed hash function. The hex-encoded result from the keyed hash function is the signature.
     
     Pseudocode for deriving a signing key
      
      kSecret = your secret access key
      
      kDate = HMAC("AWS4" + kSecret, Date)
      
      kRegion = HMAC(kDate, Region)
      
      kService = HMAC(kRegion, Service)
      
      kSigning = HMAC(kService, "aws4_request")
      
      
      signature = HexEncode(HMAC(derived signing key, string to sign))
      
      
   4. AuthorizedHeader 
   
        After you calculate the signature, add it to the request. You can add the signature to an HTTP header named Authorization
        The following pseudocode shows the construction of the Authorization header.
     
     Authorization: algorithm Credential=access key ID/credential scope, SignedHeaders=SignedHeaders, Signature=signature
     
   
   
     
     
