/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sofyan
 */

public class LatinToSunda {
    private static final int T_NONE = 0;
    private static final int T_VOCAL = 1;
    private static final int T_CONSONANT = 2;

    private static final int T_NUMBER = 3;
    //-------------------------------------------------------------------------
    // Get first oMatches characters, copied into aOutChar
    // from iInputStr, offseted by iOffset
    //-------------------------------------------------------------------------
    private String _oOutChar, _oSundaToken; // ref
    private int _oMatches, _NLoMatches; // ref
    
    private int GetChar(String iInputStr, int iOffset, String oOutChar, int oMatches)
    {
        int iret = 0;
        int ch1 = 0;
        int ch2 = 0;
        int length = 0;

        length = iInputStr.length();
        iret = T_NONE;
        if (length > iOffset)
        {
            ch1 = (int)iInputStr.charAt(iOffset);

            if (length > iOffset + 1)
                ch2 = (int)iInputStr.charAt(iOffset + 1);
            else
                ch2 = 0;

            oMatches = 1;
            if ((ch1 == 0x65 & ch2 == 0x75))
            {
                //eu
                iret = T_VOCAL;
                oMatches = 2;
            }
            else if ((ch1 == 0x61) | (ch1 == 0x69) | (ch1 == 0x75) | (ch1 == 0x65) | (ch1 == 0x6f) | (ch1 == 0xe9))
                //a, i, u, e, o, é
                iret = T_VOCAL;
            else if ((ch1 == 0x6e & ch2 == 0x67) | (ch1 == 0x6e & ch2 == 0x79))
            {
                //ng, ny
                iret = T_CONSONANT;
                oMatches = 2;
            }
            else if ((ch1 >= 0x61 & ch1 <= 0x7a))
                //consonant between a .. z
                iret = T_CONSONANT;
            else if ((ch1 >= 0x30 & ch1 <= 0x39))
                iret = T_NUMBER;
            else
                oMatches = 1;

            oOutChar = iInputStr.substring(iOffset, iOffset + oMatches);
        }
        else
        {
                oMatches = 0;
        }

        _oOutChar = oOutChar;
        _oMatches = oMatches;
        return iret;
    }

    private String GetConsonant(String iCons)
    {
        if (iCons.equals("ng"))      return "G";
        else if (iCons.equals("ny")) return "J";
        else                         return iCons;
    }

    private String GetConsonantR(String iCons)
    {
        if (iCons.equals("r"))      return "R";
        else if (iCons.equals("y")) return "Y";
        else if (iCons.equals("l")) return "L";
        else                        return iCons;
    }

    private String GetConsonantH(String iCons)
    {
        if (iCons.equals("r"))       return "Q";
        else if (iCons.equals("h"))  return "H";
        else if (iCons.equals("ng")) return "N";
        else                         return iCons + ";";
    }

    private String GetVocal(String iCons)
    {
        if (iCons.equals("a"))       return "";
        else if (iCons.equals("eu")) return "Ãª";
        else                         return iCons;
    }

    private String GetVocalM(String iCons)
    {
        if (iCons.equals("eu"))      return "ÃŠ";
        else if (iCons.equals("Ã©")) return "Ã‰";
        else                         return iCons.toUpperCase();
    }

    private String GetNumber(String iNum)
    {
        //return as it is
        return iNum;
    }

    private String NextLatinToken(String iInputStr, int oMatches, String oSundaToken)
    {
        String ret;

        int charType = 0;
        String ch1 = "";
        String ch2 = "";
        String ch3 = "";
        //string ch4 = null;
        int matches1 = 0;
        int matches2 = 0;
        int matches3 = 0;

        ret = "";
        oSundaToken = "";
        oMatches = 0;
        //ch1 = "";
        //ch2 = "";
        //ch3 = "";
        //ch4 = "";

        charType = GetChar(iInputStr, oMatches, ch1, matches1);
        ch1 = _oOutChar;
        matches1 = _oMatches;

        if ((charType == T_CONSONANT))
        {
            //(K)? ..
            ret = ret + ch1;
            oMatches = oMatches + matches1;
            oSundaToken = oSundaToken + GetConsonant(ch1);

            if ((!ch1.equals("ny") & !ch1.equals("ng") & !ch1.equals("n") & !ch1.equals("h") & !ch1.equals("r")))
            {
                charType = GetChar(iInputStr, oMatches, ch1, matches1);
                ch1 = _oOutChar;
                matches1 = _oMatches;
                if ((ch1.equals("r") | ch1.equals("y")))
                {
                    //(KR)? ..
                    ret = ret + ch1;
                    oMatches = oMatches + matches1;
                    oSundaToken = oSundaToken + GetConsonantR(ch1);
                }
            }
        }

        charType = GetChar(iInputStr, oMatches, ch1, matches1);
        ch1 = _oOutChar;
        matches1 = _oMatches;
        if ((charType == T_VOCAL))
        {
            // V ..
            if (oMatches == 0)
                oSundaToken += GetVocalM(ch1);
            else
                oSundaToken += GetVocal(ch1);

            ret = ret + ch1;
            oMatches = oMatches + matches1;

            charType = GetChar(iInputStr, oMatches, ch2, matches2);
            ch2 = _oOutChar;
            matches2 = _oMatches;
            if ((charType == T_CONSONANT))
            {
                // VK ..  
                charType = GetChar(iInputStr, oMatches + matches2, ch3, matches3);
                ch3 = _oOutChar;
                matches3 = _oMatches;
                if ((charType == T_VOCAL))
                {
                    // VKV --> get only V
                }
                else if ((!ch2.equals("ny") & !ch2.equals("ng") & !ch2.equals("n") & !ch2.equals("h") & !ch2.equals("r")) & (ch3.equals("r") | ch3.equals("y")))
                {
                    // VKR --> get only V
                }
                else
                {
                    // VKX --> get the VK
                    ret = ret + ch2;
                    oSundaToken += GetConsonantH(ch2);
                    oMatches += matches2;
                }
            }
            else
            {
                // VX --> get only V
            }
        }
        else if (oMatches == 0)
        {
            //other type
            ret = ch1;
            oSundaToken = oSundaToken + ch1;
            oMatches = matches1;
        }

        _NLoMatches = oMatches;
        _oSundaToken = oSundaToken;

        return ret;
    }

    public final String StartConvert(String iInputStr)
    {
        int inputLen = 0;
        String latinStr = "";
        String sundaStr = "";
        String latinToken;
        String sundaToken = "";

        //sundaToken = "";
        //latinStr = "";
        //sundaStr = "";

        iInputStr = iInputStr.toLowerCase();
        inputLen = iInputStr.length();

        while ((inputLen > 0))
        {
            int matches = 0;

            latinToken = NextLatinToken(iInputStr, matches, sundaToken);
            matches = _NLoMatches;
            sundaToken = _oSundaToken;

            latinStr += latinToken;
            // & ":"
            sundaStr += sundaToken;
            // & ":"

            inputLen = inputLen - matches;

            if ((inputLen > 0))
                iInputStr = iInputStr.substring((iInputStr.length() - inputLen));
        }

        return sundaStr;
    }

}
