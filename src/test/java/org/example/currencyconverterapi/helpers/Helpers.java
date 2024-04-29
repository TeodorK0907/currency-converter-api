package org.example.currencyconverterapi.helpers;

import org.example.currencyconverterapi.models.Conversion;


import java.util.Currency;

public class Helpers {

    public static Conversion createMockConversion() {
        Conversion mockConversion = new Conversion();
        mockConversion.setSourceCurrency("BGN");
        mockConversion.setTargetCurrency("USD");
        return mockConversion;
    }

    public static Currency createMockCurrency() {
        return Currency.getInstance("BGN");
    }

    public static Currency createAnotherMockCurrency() {
        return Currency.getInstance("USD");
    }

    public static String createMockExchangeRateResponse() {
        return """
                {
                    "meta": {
                        "code": 200,
                        "disclaimer": "Usage subject to terms: https://currencybeacon.com/terms"
                    },
                    "response": {
                        "date": "2024-04-12T14:33:24Z",
                        "base": "BGN",
                        "rates": {
                            "AFN": 38.8114993,
                            "ALL": 51.70720571,
                            "AMD": 214.40413164,
                            "ANG": 0.97413549,
                            "AOA": 457.44756646,
                            "ARS": 471.06780125,
                            "ATS": 7.03552967,
                            "AUD": 0.84012316,
                            "AWG": 0.97283764,
                            "AZM": 4622.25894403,
                            "AZN": 0.92445179,
                            "BAM": 1,
                            "BBD": 1.08696943,
                            "BCH": 0.00092763,
                            "BDT": 59.64290505,
                            "BEF": 20.62546336,
                            "BGN": 1,
                            "BHD": 0.20435025,
                            "BIF": 1556.3726183,
                            "BMD": 0.54348471,
                            "BND": 0.73974343,
                            "BOB": 3.76661713,
                            "BRL": 2.79052792,
                            "BSD": 0.54348471,
                            "BTC": 7.81e-6,
                            "BTN": 45.43067854,
                            "BWP": 7.44986458,
                            "BYN": 1.77733608,
                            "BYR": 17773.36077282,
                            "BZD": 1.09482359,
                            "CAD": 0.74839307,
                            "CDF": 1512.2167612,
                            "CHF": 0.4954882,
                            "CLF": 0.01397412,
                            "CLP": 522.0977307,
                            "CNH": 3.94945607,
                            "CNY": 3.93342641,
                            "COP": 2102.20964585,
                            "CRC": 274.97954359,
                            "CUC": 0.54348471,
                            "CUP": 13.02989483,
                            "CVE": 56.38015574,
                            "CYP": 0.29924584,
                            "CZK": 12.97575024,
                            "DEM": 1,
                            "DJF": 96.62481197,
                            "DKK": 3.81457248,
                            "DOGE": 2.80082169,
                            "DOP": 32.47915624,
                            "DOT": 0.06622515,
                            "DZD": 73.18677239,
                            "EEK": 8,
                            "EGP": 25.8419931,
                            "ERN": 8.15227069,
                            "ESP": 85.07181095,
                            "ETB": 30.94899616,
                            "ETH": 0.0001575,
                            "EUR": 0.51129188,
                            "FIM": 3.04000348,
                            "FJD": 1.23218007,
                            "FKP": 0.43709621,
                            "FRF": 3.35385489,
                            "GBP": 0.43709621,
                            "GEL": 1.44997944,
                            "GGP": 0.43709621,
                            "GHC": 72930.95417145,
                            "GHS": 7.29309542,
                            "GIP": 0.43709621,
                            "GMD": 36.8924327,
                            "GNF": 4668.6683069,
                            "GRD": 174.22270852,
                            "GTQ": 4.22929018,
                            "GYD": 113.51138396,
                            "HKD": 4.25924756,
                            "HNL": 13.40435122,
                            "HRK": 3.85232868,
                            "HTG": 72.13933201,
                            "HUF": 201.15406674,
                            "IDR": 8770.78878271,
                            "IEP": 0.40267508,
                            "ILS": 2.04899697,
                            "IMP": 0.43709621,
                            "INR": 45.43067854,
                            "IQD": 711.37411415,
                            "IRR": 22741.84243661,
                            "ISK": 76.94981986,
                            "ITL": 989.99913081,
                            "JEP": 0.43709621,
                            "JMD": 84.03587195,
                            "JOD": 0.38533066,
                            "JPY": 83.13264035,
                            "KES": 70.6547072,
                            "KGS": 48.4356717,
                            "KHR": 2200.17623599,
                            "KMF": 251.53911639,
                            "KPW": 489.16002086,
                            "KRW": 753.26622711,
                            "KWD": 0.16746195,
                            "KYD": 0.44669278,
                            "KZT": 244.17559776,
                            "LAK": 11432.38885986,
                            "LBP": 49845.48918574,
                            "LINK": 0.03154748,
                            "LKR": 162.15091116,
                            "LRD": 105.11010302,
                            "LSL": 10.29941731,
                            "LTC": 0.00566136,
                            "LTL": 1.76538861,
                            "LUF": 20.62546336,
                            "LUNA": 1.29086971,
                            "LVL": 0.35933593,
                            "LYD": 2.65443839,
                            "MAD": 5.45278832,
                            "MDL": 9.58877564,
                            "MGA": 2397.26697986,
                            "MGF": 11986.33489928,
                            "MKD": 31.39274368,
                            "MMK": 1140.26023877,
                            "MNT": 1845.44739463,
                            "MOP": 4.38702498,
                            "MRO": 218.1111117,
                            "MRU": 21.81111117,
                            "MTL": 0.2194976,
                            "MUR": 25.20832054,
                            "MVR": 8.36524242,
                            "MWK": 944.98766791,
                            "MXN": 9.0675475,
                            "MXV": 1.11076842,
                            "MYR": 2.59218301,
                            "MZM": 34884.80096712,
                            "MZN": 34.88480097,
                            "NAD": 10.29941731,
                            "NGN": 674.28649886,
                            "NIO": 20.1400043,
                            "NLG": 1.12673903,
                            "NOK": 5.93152182,
                            "NPR": 72.72315867,
                            "NZD": 0.91506782,
                            "OMR": 0.20923601,
                            "PAB": 0.54348471,
                            "PEN": 2.01167228,
                            "PGK": 2.05476676,
                            "PHP": 30.79389551,
                            "PKR": 151.07530911,
                            "PLN": 2.18959798,
                            "PTE": 102.50481893,
                            "PYG": 4000.77153994,
                            "QAR": 1.97828435,
                            "ROL": 25423.56320223,
                            "RON": 2.54235632,
                            "RSD": 59.88029719,
                            "RUB": 50.14293696,
                            "RWF": 706.75883579,
                            "SAR": 2.03806767,
                            "SBD": 4.51740786,
                            "SCR": 7.37528968,
                            "SDD": 31868.51611119,
                            "SDG": 318.68516111,
                            "SEK": 5.9299551,
                            "SGD": 0.73974343,
                            "SHP": 0.43709621,
                            "SIT": 122.52598641,
                            "SKK": 15.40317921,
                            "SLE": 12.30334601,
                            "SLL": 12303.34600667,
                            "SOS": 313.6875263,
                            "SPL": 0.09058079,
                            "SRD": 18.96191758,
                            "SRG": 18961.91757882,
                            "STD": 12491.75141826,
                            "STN": 12.49175142,
                            "SVC": 4.75549123,
                            "SYP": 7066.30652322,
                            "SZL": 10.29941731,
                            "THB": 19.86072288,
                            "TJS": 5.94217304,
                            "TMM": 9493.45337771,
                            "TMT": 1.89869068,
                            "TND": 1.69096558,
                            "TOP": 1.29313911,
                            "TRL": 17587029.849125408,
                            "TRY": 17.58702985,
                            "TTD": 3.68311986,
                            "TVD": 0.84012316,
                            "TWD": 17.55888757,
                            "TZS": 1396.16880228,
                            "UAH": 21.38345074,
                            "UGX": 2067.03413408,
                            "UNI": 0.06069045,
                            "USD": 0.54348471,
                            "UYU": 21.01169444,
                            "UZS": 6891.23697805,
                            "VAL": 989.99913081,
                            "VEB": 1966719157.188899,
                            "VED": 19.66669008,
                            "VEF": 1966669.00837892,
                            "VES": 19.66669008,
                            "VND": 13604.47114856,
                            "VUV": 65.61780333,
                            "WST": 1.48977234,
                            "XAF": 335.38548852,
                            "XAG": 0.01840333,
                            "XAU": 0.00022504,
                            "XBT": 7.81e-6,
                            "XCD": 1.46850845,
                            "XDR": 0.41302447,
                            "XLM": 4.30098096,
                            "XOF": 335.38548852,
                            "XPD": 0.00050553,
                            "XPF": 61.01335098,
                            "XPT": 0.0005436,
                            "XRP": 0.91229511,
                            "YER": 135.97248178,
                            "ZAR": 10.29941731,
                            "ZMK": 13574.38616911,
                            "ZMW": 13.57438617,
                            "ZWD": 196.68711747,
                            "ZWL": 16761.92910441,
                            "ADA": 0.95846205,
                            "AED": 1.99594761
                        }
                    },
                    "date": "2024-04-12T14:33:24Z",
                    "base": "BGN",
                    "rates": {
                        "AFN": 38.8114993,
                        "ALL": 51.70720571,
                        "AMD": 214.40413164,
                        "ANG": 0.97413549,
                        "AOA": 457.44756646,
                        "ARS": 471.06780125,
                        "ATS": 7.03552967,
                        "AUD": 0.84012316,
                        "AWG": 0.97283764,
                        "AZM": 4622.25894403,
                        "AZN": 0.92445179,
                        "BAM": 1,
                        "BBD": 1.08696943,
                        "BCH": 0.00092763,
                        "BDT": 59.64290505,
                        "BEF": 20.62546336,
                        "BGN": 1,
                        "BHD": 0.20435025,
                        "BIF": 1556.3726183,
                        "BMD": 0.54348471,
                        "BND": 0.73974343,
                        "BOB": 3.76661713,
                        "BRL": 2.79052792,
                        "BSD": 0.54348471,
                        "BTC": 7.81e-6,
                        "BTN": 45.43067854,
                        "BWP": 7.44986458,
                        "BYN": 1.77733608,
                        "BYR": 17773.36077282,
                        "BZD": 1.09482359,
                        "CAD": 0.74839307,
                        "CDF": 1512.2167612,
                        "CHF": 0.4954882,
                        "CLF": 0.01397412,
                        "CLP": 522.0977307,
                        "CNH": 3.94945607,
                        "CNY": 3.93342641,
                        "COP": 2102.20964585,
                        "CRC": 274.97954359,
                        "CUC": 0.54348471,
                        "CUP": 13.02989483,
                        "CVE": 56.38015574,
                        "CYP": 0.29924584,
                        "CZK": 12.97575024,
                        "DEM": 1,
                        "DJF": 96.62481197,
                        "DKK": 3.81457248,
                        "DOGE": 2.80082169,
                        "DOP": 32.47915624,
                        "DOT": 0.06622515,
                        "DZD": 73.18677239,
                        "EEK": 8,
                        "EGP": 25.8419931,
                        "ERN": 8.15227069,
                        "ESP": 85.07181095,
                        "ETB": 30.94899616,
                        "ETH": 0.0001575,
                        "EUR": 0.51129188,
                        "FIM": 3.04000348,
                        "FJD": 1.23218007,
                        "FKP": 0.43709621,
                        "FRF": 3.35385489,
                        "GBP": 0.43709621,
                        "GEL": 1.44997944,
                        "GGP": 0.43709621,
                        "GHC": 72930.95417145,
                        "GHS": 7.29309542,
                        "GIP": 0.43709621,
                        "GMD": 36.8924327,
                        "GNF": 4668.6683069,
                        "GRD": 174.22270852,
                        "GTQ": 4.22929018,
                        "GYD": 113.51138396,
                        "HKD": 4.25924756,
                        "HNL": 13.40435122,
                        "HRK": 3.85232868,
                        "HTG": 72.13933201,
                        "HUF": 201.15406674,
                        "IDR": 8770.78878271,
                        "IEP": 0.40267508,
                        "ILS": 2.04899697,
                        "IMP": 0.43709621,
                        "INR": 45.43067854,
                        "IQD": 711.37411415,
                        "IRR": 22741.84243661,
                        "ISK": 76.94981986,
                        "ITL": 989.99913081,
                        "JEP": 0.43709621,
                        "JMD": 84.03587195,
                        "JOD": 0.38533066,
                        "JPY": 83.13264035,
                        "KES": 70.6547072,
                        "KGS": 48.4356717,
                        "KHR": 2200.17623599,
                        "KMF": 251.53911639,
                        "KPW": 489.16002086,
                        "KRW": 753.26622711,
                        "KWD": 0.16746195,
                        "KYD": 0.44669278,
                        "KZT": 244.17559776,
                        "LAK": 11432.38885986,
                        "LBP": 49845.48918574,
                        "LINK": 0.03154748,
                        "LKR": 162.15091116,
                        "LRD": 105.11010302,
                        "LSL": 10.29941731,
                        "LTC": 0.00566136,
                        "LTL": 1.76538861,
                        "LUF": 20.62546336,
                        "LUNA": 1.29086971,
                        "LVL": 0.35933593,
                        "LYD": 2.65443839,
                        "MAD": 5.45278832,
                        "MDL": 9.58877564,
                        "MGA": 2397.26697986,
                        "MGF": 11986.33489928,
                        "MKD": 31.39274368,
                        "MMK": 1140.26023877,
                        "MNT": 1845.44739463,
                        "MOP": 4.38702498,
                        "MRO": 218.1111117,
                        "MRU": 21.81111117,
                        "MTL": 0.2194976,
                        "MUR": 25.20832054,
                        "MVR": 8.36524242,
                        "MWK": 944.98766791,
                        "MXN": 9.0675475,
                        "MXV": 1.11076842,
                        "MYR": 2.59218301,
                        "MZM": 34884.80096712,
                        "MZN": 34.88480097,
                        "NAD": 10.29941731,
                        "NGN": 674.28649886,
                        "NIO": 20.1400043,
                        "NLG": 1.12673903,
                        "NOK": 5.93152182,
                        "NPR": 72.72315867,
                        "NZD": 0.91506782,
                        "OMR": 0.20923601,
                        "PAB": 0.54348471,
                        "PEN": 2.01167228,
                        "PGK": 2.05476676,
                        "PHP": 30.79389551,
                        "PKR": 151.07530911,
                        "PLN": 2.18959798,
                        "PTE": 102.50481893,
                        "PYG": 4000.77153994,
                        "QAR": 1.97828435,
                        "ROL": 25423.56320223,
                        "RON": 2.54235632,
                        "RSD": 59.88029719,
                        "RUB": 50.14293696,
                        "RWF": 706.75883579,
                        "SAR": 2.03806767,
                        "SBD": 4.51740786,
                        "SCR": 7.37528968,
                        "SDD": 31868.51611119,
                        "SDG": 318.68516111,
                        "SEK": 5.9299551,
                        "SGD": 0.73974343,
                        "SHP": 0.43709621,
                        "SIT": 122.52598641,
                        "SKK": 15.40317921,
                        "SLE": 12.30334601,
                        "SLL": 12303.34600667,
                        "SOS": 313.6875263,
                        "SPL": 0.09058079,
                        "SRD": 18.96191758,
                        "SRG": 18961.91757882,
                        "STD": 12491.75141826,
                        "STN": 12.49175142,
                        "SVC": 4.75549123,
                        "SYP": 7066.30652322,
                        "SZL": 10.29941731,
                        "THB": 19.86072288,
                        "TJS": 5.94217304,
                        "TMM": 9493.45337771,
                        "TMT": 1.89869068,
                        "TND": 1.69096558,
                        "TOP": 1.29313911,
                        "TRL": 17587029.849125408,
                        "TRY": 17.58702985,
                        "TTD": 3.68311986,
                        "TVD": 0.84012316,
                        "TWD": 17.55888757,
                        "TZS": 1396.16880228,
                        "UAH": 21.38345074,
                        "UGX": 2067.03413408,
                        "UNI": 0.06069045,
                        "USD": 0.54348471,
                        "UYU": 21.01169444,
                        "UZS": 6891.23697805,
                        "VAL": 989.99913081,
                        "VEB": 1966719157.188899,
                        "VED": 19.66669008,
                        "VEF": 1966669.00837892,
                        "VES": 19.66669008,
                        "VND": 13604.47114856,
                        "VUV": 65.61780333,
                        "WST": 1.48977234,
                        "XAF": 335.38548852,
                        "XAG": 0.01840333,
                        "XAU": 0.00022504,
                        "XBT": 7.81e-6,
                        "XCD": 1.46850845,
                        "XDR": 0.41302447,
                        "XLM": 4.30098096,
                        "XOF": 335.38548852,
                        "XPD": 0.00050553,
                        "XPF": 61.01335098,
                        "XPT": 0.0005436,
                        "XRP": 0.91229511,
                        "YER": 135.97248178,
                        "ZAR": 10.29941731,
                        "ZMK": 13574.38616911,
                        "ZMW": 13.57438617,
                        "ZWD": 196.68711747,
                        "ZWL": 16761.92910441,
                        "ADA": 0.95846205,
                        "AED": 1.99594761
                    }
                }
                """;
    }

    public static String createInvalidMockResponse() {
        return """
                {
                  "meta": {
                           "code": 400,
                            "disclaimer": "Usage subject to terms: https://currencybeacon.com/terms"
                          }
                }""";
    }

    public static String createMockConversionResponse() {
        return """
                {
                    "meta": {
                        "code": 200,
                        "disclaimer": "Usage subject to terms: https://currencybeacon.com/terms"
                    },
                    "response": {
                        "timestamp": 1714209350,
                        "date": "2024-04-27",
                        "from": "USD",
                        "to": "BGN",
                        "amount": 20,
                        "value": 36.5665814
                    },
                    "timestamp": 1714209350,
                    "date": "2024-04-27",
                    "from": "USD",
                    "to": "BGN",
                    "amount": 20,
                    "value": 36.5665814
                }
                """;
    }

    public static String createInvalidMockConversionAmount() {
        return """
                {
                    "meta": {
                        "code": 200,
                        "disclaimer": "Usage subject to terms: https://currencybeacon.com/terms"
                    },
                    "response": {
                        "timestamp": 1714209350,
                        "date": "2024-04-27",
                        "from": "USD",
                        "to": "BGN",
                        "amount": 20,
                        "value": null
                    },
                }
                """;
    }
}
