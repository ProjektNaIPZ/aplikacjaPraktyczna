import java.io.IOException;
import java.security.KeyPair;
import java.util.*;

import com.bigchaindb.api.TransactionsApi;
import com.bigchaindb.builders.BigchainDbConfigBuilder;
import com.bigchaindb.builders.BigchainDbTransactionBuilder;
import com.bigchaindb.constants.Operations;
import com.bigchaindb.model.*;
import com.bigchaindb.util.Base58;

import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import okhttp3.Response;

import static com.bigchaindb.api.AssetsApi.getAssets;

public class Main {
    private static String nt = "";
    public static void main(String args[]) throws Exception {
        Scanner scan = new Scanner(System.in);
        List<Pytanie> ankieta = new LinkedList();
        boolean flag = true;
        int a = 1;
        String p1;
        int liczbaOdpowiedzi;


        System.out.println("Dodaj pytanie/a i odpowiedzi do ankiety:");
        do{
            System.out.print(a + ". Pytanie: ");
            p1 = scan.nextLine();
            System.out.print("Podaj ilość odpowiedzi: ");
            liczbaOdpowiedzi = scan.nextInt();
            scan.nextLine();
            String[] odps = new String[liczbaOdpowiedzi];
            for(int i = 0; i < liczbaOdpowiedzi; i++){
                System.out.println("Odpowiedz " + (i+1) + " ");
                odps[i] = scan.next();
                scan.nextLine();
            }
            ankieta.add(new Pytanie(p1, odps));
            a++;
            System.out.println("dodać kolejne pytanie? [y/n]");
            p1 = scan.next();
            scan.nextLine();
            if(p1.equals("n")){
                flag = false;
            }
        }
        while(flag);

        for (Pytanie temp : ankieta) {
            System.out.println(temp.pytanie);
            for (String aaa : temp.odpowiedzi) {
                System.out.println(aaa);
            }
            System.out.println();
        }



        wyslij(ankieta);

        //transakcje wyświetl
        System.out.println("####################\n\n");
        //"05d69de715bb8846c0518185825597673eca23708ed8d786db43439a93e69c5a"
        Transaction bbb = TransactionsApi.getTransactionById(nt);
        System.out.println(bbb);
//
//        System.out.println("####################\n\n\n\n");
//        Block blok = getBlock("92");
//        System.out.println(blok.getHeight());
    }



    private void onSuccess(Response response) {
        System.out.println("Transaction posted successfully");
    }

    private void onFailure() {
        System.out.println("Transaction failed");
    }

    private GenericCallback handleServerResponse() {
        //define callback methods to verify response from BigchainDBServer
        GenericCallback callback = new GenericCallback() {

            @Override
            public void transactionMalformed(Response response) {
                System.out.println("malformed " + response.message());
                onFailure();
            }

            @Override
            public void pushedSuccessfully(Response response) {
                System.out.println("pushedSuccessfully");
                onSuccess(response);
            }

            @Override
            public void otherError(Response response) {
                System.out.println("otherError" + response.message());
                onFailure();
            }
        };

        return callback;
    }


    //konfiguracja połączenia
    public static void setConfig() {
        BigchainDbConfigBuilder
                .baseUrl("http://localhost:9984/") //http://testnet.bigchaindb.com //nowy testnet: https://test.ipdb.io/ //http://localhost:9984/
                .addToken("app_id", "")
                .addToken("app_key", "").setup();

    }

    //generowanie kluczy EdDSA do podpisu i weryfikacji transakcji
    public static KeyPair getKeys() {
        //  prepare your keys
        net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
        KeyPair keyPair = edDsaKpg.generateKeyPair();
        System.out.println("(*) Keys Generated..");
        return keyPair;

    }

    //performs CREATE transactions on BigchainDB network
    public String doCreate(Map<String, String> assetData, MetaData metaData, KeyPair keys) throws Exception {

        try {
            //build and send CREATE transaction
            Transaction transaction = null;

            transaction = BigchainDbTransactionBuilder
                    .init()
                    .addAssets(assetData, TreeMap.class)
                    .addMetaData(metaData)
                    .operation(Operations.CREATE)
                    .buildAndSign((EdDSAPublicKey) keys.getPublic(), (EdDSAPrivateKey) keys.getPrivate())
                    .sendTransaction(handleServerResponse());

            System.out.println("(*) CREATE Transaction sent.. - " + transaction.getId());
            nt = transaction.getId();
            return transaction.getId();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //performs TRANSFER operations on CREATED assets
    public void doTransfer(String txId, MetaData metaData, KeyPair keys) throws Exception {

        Map<String, String> assetData = new TreeMap<String, String>();
        assetData.put("id", txId);

        try {
            //which transaction you want to fulfill?
            FulFill fulfill = new FulFill();
            fulfill.setOutputIndex("0"); // ?
            fulfill.setTransactionId(txId);


            //build and send TRANSFER transaction
            Transaction transaction = BigchainDbTransactionBuilder
                    .init()
                    .addInput(null, fulfill, (EdDSAPublicKey) keys.getPublic())
                    .addOutput("0", (EdDSAPublicKey) keys.getPublic())
                    .addAssets(txId, String.class)
                    .addMetaData(metaData)
                    .operation(Operations.TRANSFER)
                    .buildAndSign((EdDSAPublicKey) keys.getPublic(), (EdDSAPrivateKey) keys.getPrivate())
                    .sendTransaction(handleServerResponse());

            System.out.println("(*) TRANSFER Transaction sent.. - " + transaction.getId());
            //System.out.println("(*) ASSET_ID - " + transaction.getAsset());

//            Assets ast = getAssets();
//            System.out.println("######## ASSET ##########");
//            System.out.println(ast.getAssets());
//            System.out.println("######## ASSET ##########");


//            System.out.println("###" + transaction.getId());
//            Transaction aaa = TransactionsApi.getTransactionById("cbe14978e6223205736a1564856d847f5bd5a86e3f4ec0b3981094d4842b0791"); //
//            System.out.println("######## Transakcja ###########");
//            System.out.println(aaa);
//            System.out.println("######## Transakcja ###########");
            //aaa.getAsset();





        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void wyslij(List<Pytanie> ankieta) throws Exception {
        Main examples = new Main();

        //set configuration
        Main.setConfig();

        //generate Keys
        KeyPair keys = Main.getKeys();

        System.out.println(Base58.encode(keys.getPublic().getEncoded()));
        System.out.println(Base58.encode(keys.getPrivate().getEncoded()));

        // create New asset
        Map<String, String> assetData = new TreeMap<String, String>() {{
            int i = 1;
            for (Pytanie temp : ankieta) {
                put("pytanie"+i, temp.pytanie);
                i++;
                int a = 1;
                for (String odp : temp.odpowiedzi) {
                    put("odpowiedz"+a, odp);
                    a++;
                }
            }
        }};
        //Thread.sleep(5000);
        System.out.println("(*) Assets Prepared..");


        // create metadata
        MetaData metaData = new MetaData();
        metaData.setMetaData("Skąd", "Polska");
        System.out.println("(*) Metadata Prepared..");

        //execute CREATE transaction
        String txId = examples.doCreate(assetData, metaData, keys);

        //create transfer metadata
        MetaData transferMetadata = new MetaData();
        transferMetadata.setMetaData("do", "Niemcy");
        System.out.println("(*) Transfer Metadata Prepared..");

        //let the transaction commit in block
        Thread.sleep(5000);

        //execute TRANSFER transaction on the CREATED asset
        examples.doTransfer(txId, transferMetadata, keys);

//        Assets ast = getAssets(assetData.get("61e8235c0254c0001e1dac78"));
//        System.out.println(ast.getAssets());
    }
}