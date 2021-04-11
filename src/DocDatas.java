import java.util.HashMap;
import java.util.Map;

public class DocDatas {

    Map<String,DataStruct> datas = new HashMap<>();

    public DocDatas() {
        datas.put(
            "01",
            new DataStruct(
                    "Identifiant unique du document",
                    0,
                    Integer.MAX_VALUE)
        );

        datas.put(
                "02",
                new DataStruct(
                        "Catégorie de document",
                        0,
                        Integer.MAX_VALUE)
        );

        datas.put(
                "03",
                new DataStruct(
                        "Sous-catégorie de document",
                        0,
                        Integer.MAX_VALUE)
        );


        datas.put(
                "04",
                new DataStruct(
                        "Application de composition",
                        0,
                        Integer.MAX_VALUE)
        );

        datas.put(
                "05",
                new DataStruct(
                        "Version de l’application de composition",
                        0,
                        Integer.MAX_VALUE)
        );

        datas.put(
                "06",
                new DataStruct(
                        "Date de l’association entre le document et le code 2D-Doc",
                        4,
                        4)
        );

        datas.put(
                "07",
                new DataStruct(
                        "Heure de l’association entre le document et le code 2D-Doc",
                        6,
                        6)
        );

        datas.put(
                "08",
                new DataStruct(
                        "Date d’expiration du document",
                        4,
                        4)
        );

        datas.put(
                "09",
                new DataStruct(
                        "Nombre de pages du document",
                        4,
                        4)
        );

        datas.put(
                "0A",
                new DataStruct(
                        "Editeur du 2D-Doc",
                        9,
                        9)
        );

        datas.put(
                "0B",
                new DataStruct(
                        "Intégrateur du 2D-Doc",
                        9,
                        9)
        );

        datas.put(
                "0C",
                new DataStruct(
                        "URL du document",
                        0,
                        Integer.MAX_VALUE)
        );

        datas.put(
                "10",
                new DataStruct(
                        "Ligne 1 de la norme adresse postale du bénéficiaire de la prestation",
                        0,
                        38)
        );

        datas.put(
                "11",
                new DataStruct(
                        "Qualité et/ou titre de la personne bénéficiaire de la prestation",
                        0,
                        38)
        );

        datas.put(
                "12",
                new DataStruct(
                        "Prénom de la personne bénéficiaire de la prestation",
                        0,
                        38)
        );

        datas.put(
                "13",
                new DataStruct(
                        "Nom de la personne bénéficiaire de la prestation",
                        0,
                        38)
        );

        datas.put(
                "14",
                new DataStruct(
                        "Ligne 1 de la norme adresse postale du destinataire de la facture",
                        0,
                        38)
        );

        datas.put(
                "15",
                new DataStruct(
                        "Qualité et/ou titre de la personne destinataire de la facture",
                        0,
                        38)
        );

        datas.put(
                "16",
                new DataStruct(
                        "Prénom de la personne destinataire de la facture",
                        0,
                        38)
        );

        datas.put(
                "17",
                new DataStruct(
                        "Nom de la personne destinataire de la facture",
                        0,
                        38)
        );

        datas.put(
                "18",
                new DataStruct(
                        "Numéro de la facture",
                        0,
                        Integer.MAX_VALUE)
        );

        datas.put(
                "19",
                new DataStruct(
                        "Numéro de client",
                        0,
                        Integer.MAX_VALUE)
        );

        datas.put(
                "1A",
                new DataStruct(
                        "Numéro du contrat",
                        0,
                        Integer.MAX_VALUE)
        );

        datas.put(
                "1B",
                new DataStruct(
                        "Identifiant du souscripteur du contrat",
                        0,
                        Integer.MAX_VALUE)
        );

        datas.put(
                "1C",
                new DataStruct(
                        "Date d’effet du contrat",
                        8,
                        8)
        );

        datas.put(
                "1D",
                new DataStruct(
                        "Montant TTC de la facture",
                        0,
                        16)
        );

        datas.put(
                "1E",
                new DataStruct(
                        "Numéro de téléphone du bénéficiaire de la prestation",
                        0,
                        30)
        );

        datas.put(
                "1F",
                new DataStruct(
                        "Numéro de téléphone du destinataire de la facture",
                        0,
                        30)
        );

        datas.put(
                "1G",
                new DataStruct(
                        "Présence d’un co-bénéficiaire de la prestation non mentionné dans le code",
                        1,
                        1)
        );

        datas.put(
                "1H",
                new DataStruct(
                        "Présence d’un co-destinataire de la facture non mentionné dans le code",
                        1,
                        1)
        );

        datas.put(
                "1I",
                new DataStruct(
                        "Ligne 1 de la norme adresse postale du co-bénéficiaire de la prestation",
                        0,
                        38)
        );

        datas.put(
                "1J",
                new DataStruct(
                        "Qualité et/ou titre du co-bénéficiaire de la prestation",
                        0,
                        38)
        );

        datas.put(
                "1K",
                new DataStruct(
                        "Prénom du co-bénéficiaire de la prestation",
                        0,
                        38)
        );

        datas.put(
                "1L",
                new DataStruct(
                        "Nom du co-bénéficiaire de la prestation",
                        0,
                        38)
        );

        datas.put(
                "1M",
                new DataStruct(
                        "Ligne 1 de la norme adresse postale du co-destinataire de la facture",
                        0,
                        38)
        );

        datas.put(
                "1N",
                new DataStruct(
                        "Qualité et/ou titre du co-destinataire de la facture",
                        0,
                        38)
        );

        datas.put(
                "1O",
                new DataStruct(
                        "Prénom du co-destinataire de la facture",
                        0,
                        38)
        );

        datas.put(
                "1P",
                new DataStruct(
                        "Nom du co-destinataire de la facture",
                        0,
                        38)
        );

        datas.put(
                "20",
                new DataStruct(
                        "Ligne 2 de la norme adresse postale du point de service des prestations",
                        0,
                        38)
        );

        datas.put(
                "21",
                new DataStruct(
                        "Ligne 3 de la norme adresse postale du point de service des prestations",
                        0,
                        38)
        );

        datas.put(
                "22",
                new DataStruct(
                        "Ligne 4 de la norme adresse postale du point de service des prestations",
                        0,
                        38)
        );

        datas.put(
                "23",
                new DataStruct(
                        "Ligne 5 de la norme adresse postale du point de service des prestations",
                        0,
                        38)
        );

        datas.put(
                "24",
                new DataStruct(
                        "Code postal ou code cedex du point de service des prestations",
                        5,
                        5)
        );

        datas.put(
                "25",
                new DataStruct(
                        "Localité de destination ou libellé cedex du point de service des prestations",
                        0,
                        32)
        );

        datas.put(
                "26",
                new DataStruct(
                        "Pays de service des prestations",
                        2,
                        2)
        );

        datas.put(
                "27",
                new DataStruct(
                        "Ligne 2 de la norme adresse postale du destinataire de la facture",
                        0,
                        38)
        );

        datas.put(
                "28",
                new DataStruct(
                        "Ligne 3 de la norme adresse postale du destinataire de lafacture",
                        0,
                        38)
        );

        datas.put(
                "29",
                new DataStruct(
                        "Ligne 4 de la norme adresse postale du destinataire de la facture",
                        0,
                        38)
        );

        datas.put(
                "2A",
                new DataStruct(
                        "Ligne 5 de la norme adresse postale du destinataire de la facture",
                        0,
                        38)
        );

        datas.put(
                "2B",
                new DataStruct(
                        "Code postal ou code cedex du destinataire de la facture",
                        5,
                        5)
        );

        datas.put(
                "2C",
                new DataStruct(
                        "Localité de destination ou libellé cedex du destinataire de la facture",
                        0,
                        32)
        );

        datas.put(
                "2D",
                new DataStruct(
                        "Pays du destinataire de la facture",
                        2,
                        2)
        );

        datas.put(
                "30",
                new DataStruct(
                        "Qualité Nom et Prénom",
                        0,
                        140)
        );

        datas.put(
                "31",
                new DataStruct(
                        "Code IBAN",
                        14,
                        38)
        );

        datas.put(
                "32",
                new DataStruct(
                        "Code BIC/SWIFT",
                        8,
                        11)
        );

        datas.put(
                "33",
                new DataStruct(
                        "Code BBAN",
                        0,
                        30)
        );

        datas.put(
                "34",
                new DataStruct(
                        "Pays de localisation du compte",
                        2,
                        2)
        );

        datas.put(
                "35",
                new DataStruct(
                        "Identifiant SEPAmail (QXBAN)",
                        14,
                        34)
        );

        datas.put(
                "36",
                new DataStruct(
                        "Date de début de période",
                        4,
                        4)
        );

        datas.put(
                "37",
                new DataStruct(
                        "Date de fin de période",
                        4,
                        4)
        );

        datas.put(
                "38",
                new DataStruct(
                        "Solde compte début de période",
                        0,
                        11)
        );

        datas.put(
                "39",
                new DataStruct(
                        "Solde compte fin de période",
                        0,
                        11)
        );

        datas.put(
                "1N",
                new DataStruct(
                        "",
                        0,
                        38)
        );

        datas.put(
                "40",
                new DataStruct(
                        "Numéro fiscal",
                        13,
                        13)
        );

        datas.put(
                "41",
                new DataStruct(
                        "Revenu fiscal de référence",
                        0,
                        Integer.MAX_VALUE)
        );

        datas.put(
                "42",
                new DataStruct(
                        "Situation du foyer",
                        0,
                        Integer.MAX_VALUE)
        );

        datas.put(
                "43",
                new DataStruct(
                        "Nombre de parts",
                        0,
                        Integer.MAX_VALUE)
        );

        datas.put(
                "44",
                new DataStruct(
                        "Référence d’avis d’impôt",
                        13,
                        13)
        );

        datas.put(
                "50",
                new DataStruct(
                        "SIRET de l’employeur",
                        14,
                        14)
        );

        datas.put(
                "51",
                new DataStruct(
                        "Nombre d’heures travaillées",
                        6,
                        6)
        );

        datas.put(
                "52",
                new DataStruct(
                        "Cumul du nombre d’heures travaillées",
                        7,
                        7)
        );

        datas.put(
                "53",
                new DataStruct(
                        "Début de période",
                        4,
                        4)
        );

        datas.put(
                "1N",
                new DataStruct(
                        "",
                        0,
                        38)
        );

        datas.put(
                "54",
                new DataStruct(
                        "Fin de période",
                        4,
                        4)
        );

        datas.put(
                "55",
                new DataStruct(
                        "Date de début de contrat",
                        8,
                        8)
        );

        datas.put(
                "56",
                new DataStruct(
                        "Date de fin de contrat",
                        4,
                        4)
        );

        datas.put(
                "57",
                new DataStruct(
                        "Date de signature du contrat",
                        8,
                        8)
        );

        datas.put(
                "58",
                new DataStruct(
                        "Salaire net imposable",
                        0,
                        11)
        );

        datas.put(
                "59",
                new DataStruct(
                        "",
                        0,
                        12)
        );

        datas.put(
                "5A",
                new DataStruct(
                        "Salaire brut du mois",
                        0,
                        11)
        );

        datas.put(
                "5B",
                new DataStruct(
                        "Cumul du salaire brut",
                        0,
                        12)
        );

        datas.put(
                "5C",
                new DataStruct(
                        "Salaire net",
                        0,
                        11)
        );

        datas.put(
                "5D",
                new DataStruct(
                        "Ligne 2 de la norme adresse postale de l’employeur",
                        0,
                        38)
        );

        datas.put(
                "5E",
                new DataStruct(
                        "Ligne 3 de la norme adresse postale de l’employeur",
                        0,
                        38)
        );

        datas.put(
                "5F",
                new DataStruct(
                        "Ligne 4 de la norme adresse postale de l’employeur",
                        0,
                        38)
        );

        datas.put(
                "5G",
                new DataStruct(
                        "Ligne 5 de la norme adresse postale de l’employeur",
                        0,
                        38)
        );

        datas.put(
                "5H",
                new DataStruct(
                        "Code postal ou code cedex de l’employeur",
                        5,
                        5)
        );

        datas.put(
                "5I",
                new DataStruct(
                        "Localité de destination ou libellé cedex de l’employeur",
                        0,
                        32)
        );

        datas.put(
                "5J",
                new DataStruct(
                        "Pays de l’employeur",
                        2,
                        2)
        );

        datas.put(
                "5K",
                new DataStruct(
                        "Identifiant Cotisant Prestations Sociales",
                        0,
                        50)
        );

        datas.put(
                "60",
                new DataStruct(
                        "Liste des prénoms",
                        0,
                        60)
        );

        datas.put(
                "61",
                new DataStruct(
                        "Prénom",
                        0,
                        20)
        );

        datas.put(
                "62",
                new DataStruct(
                        "Nom patronymique",
                        0,
                        38)
        );

        datas.put(
                "1N",
                new DataStruct(
                        "",
                        0,
                        38)
        );

        datas.put(
                "63",
                new DataStruct(
                        "Nom d’usage",
                        0,
                        38)
        );

        datas.put(
                "64",
                new DataStruct(
                        "Nom d’épouse/époux",
                        0,
                        38)
        );

        datas.put(
                "65",
                new DataStruct(
                        "Type de pièce d’identité",
                        2,
                        2)
        );

        datas.put(
                "66",
                new DataStruct(
                        "Numéro de la pièce d’identité",
                        0,
                        38)
        );

        datas.put(
                "67",
                new DataStruct(
                        "Nationalité",
                        2,
                        2)
        );

        datas.put(
                "68",
                new DataStruct(
                    "Genre",
                    1,
                    1)
        );

        datas.put(
                "69",
                new DataStruct(
                        "Date de naissance",
                        8,
                        8)
        );

        datas.put(
                "6A",
                new DataStruct(
                        "Lieu de naissance",
                        0,
                        32)
        );

        datas.put(
                "6B",
                new DataStruct(
                        "Département du bureau émetteur",
                        3,
                        3)
        );

        datas.put(
                "6C",
                new DataStruct(
                        "Pays de naissance",
                        2,
                        2)
        );

        datas.put(
                "6D",
                new DataStruct(
                        "Nom et prénom du père",
                        0,
                        60)
        );

        datas.put(
                "1N",
                new DataStruct(
                        "",
                        1,
                        1)
        );

        datas.put(
                "6E",
                new DataStruct(
                        "Nom et prénom de la mère",
                        0,
                        60)
        );

        datas.put(
                "6F",
                new DataStruct(
                        "Machine Readable Zone (Zone de Lecture Automatique, ZLA)",
                        0,
                        90)
        );

        datas.put(
                "6G",
                new DataStruct(
                        "Nom",
                        1,
                        38)
        );

        datas.put(
                "6H",
                new DataStruct(
                        "Civilité",
                        1,
                        10)
        );

        datas.put(
                "6I",
                new DataStruct(
                        "Pays émetteur",
                        2,
                        2)
        );

        datas.put(
                "6J",
                new DataStruct(
                        "Type de document étranger",
                        1,
                        1)
        );

        datas.put(
                "6K",
                new DataStruct(
                        "Numéro de la demandede document étranger",
                        19,
                        19)
        );

        datas.put(
                "6L",
                new DataStruct(
                        "Date de dépôt de la demande",
                        8,
                        8)
        );

        datas.put(
                "6M",
                new DataStruct(
                        "Catégorie du titre",
                        0,
                        40)
        );

        datas.put(
                "6N",
                new DataStruct(
                        "Date de début de validité",
                        8,
                        8)
        );

        datas.put(
                "6O",
                new DataStruct(
                        "Date de fin de validité",
                        8,
                        8)
        );

        datas.put(
                "6P",
                new DataStruct(
                        "Autorisation",
                        0,
                        40)
        );

        datas.put(
                "6Q",
                new DataStruct(
                        "Numéro d’étranger",
                        0,
                        10)
        );

        datas.put(
                "6R",
                new DataStruct(
                        "Numéro de visa",
                        12,
                        12)
        );

        datas.put(
                "6S",
                new DataStruct(
                        "Ligne 2 de l'adresse postale du domicile",
                        0,
                        38)
        );

        datas.put(
                "6T",
                new DataStruct(
                        "Ligne 3 de l'adresse postale du domicile",
                        0,
                        38)
        );

        datas.put(
                "6U",
                new DataStruct(
                        "Ligne 4 de l'adresse postale du domicile",
                        0,
                        38)
        );

        datas.put(
                "6V",
                new DataStruct(
                        "Ligne 5 de l'adresse postale du domicile",
                        0,
                        38)
        );

        datas.put(
                "6W",
                new DataStruct(
                        "Code postal ou code cedex de l'adresse postale du domicile",
                        5,
                        5)
        );

        datas.put(
                "6X",
                new DataStruct(
                        "Commune de l'adresse postale du domicile",
                        0,
                        32)
        );

        datas.put(
                "6Y",
                new DataStruct(
                        "Code pays de l'adresse postale du domicile",
                        2,
                        2)
        );

        datas.put(
                "70",
                new DataStruct(
                        "Date et heure du décès",
                        12,
                        12)
        );

        datas.put(
                "71",
                new DataStruct(
                        "Date et heure du constat de décès",
                        12,
                        12)
        );

        datas.put(
                "72",
                new DataStruct(
                        "Nom du défunt",
                        0,
                        38)
        );

        datas.put(
                "73",
                new DataStruct(
                        "Prénoms du défunt",
                        0,
                        60)
        );

        datas.put(
                "74",
                new DataStruct(
                        "Nom de jeune filledu défunt",
                        0,
                        38)
        );

        datas.put(
                "75",
                new DataStruct(
                        "Date de naissance du défunt",
                        8,
                        8)
        );

        datas.put(
                "76",
                new DataStruct(
                        "Genre du défunt",
                        1,
                        1)
        );

        datas.put(
                "77",
                new DataStruct(
                        "Commune dedécès",
                        0,
                        45)
        );

        datas.put(
                "78",
                new DataStruct(
                        "Code postal de la commune de décès",
                        5,
                        5)
        );

        datas.put(
                "79",
                new DataStruct(
                        "Adresse du domicile du défunt",
                        0,
                        114)
        );

        datas.put(
                "7A",
                new DataStruct(
                        "Code postal du domicile du défunt",
                        5,
                        5)
        );

        datas.put(
                "7B",
                new DataStruct(
                        "Commune du domicile du défunt",
                        0,
                        45)
        );

        datas.put(
                "7C",
                new DataStruct(
                        "Obstacle médico-légal",
                        1,
                        1)
        );

        datas.put(
                "7D",
                new DataStruct(
                        "Mise en bière",
                        1,
                        1)
        );

        datas.put(
                "7E",
                new DataStruct(
                        "Obstacle aux soins de conservation",
                        1,
                        1)
        );

        datas.put(
                "7F",
                new DataStruct(
                        "Obstacle aux dons du corps",
                        1,
                        1)
        );

        datas.put(
                "7G",
                new DataStruct(
                        "Recherche de la cause du décès",
                        1,
                        1)
        );

        datas.put(
                "7H",
                new DataStruct(
                        "Délai de transport du corps",
                        2,
                        2)
        );

        datas.put(
                "7I",
                new DataStruct(
                        "Prothèse avec pile",
                        1,
                        1)
        );

        datas.put(
                "1N",
                new DataStruct(
                        "",
                        1,
                        1)
        );

        datas.put(
                "7J",
                new DataStruct(
                        "Retrait de la pile de prothèse",
                        1,
                        1)
        );

        datas.put(
                "7K",
                new DataStruct(
                        "Code NNC",
                        13,
                        13)
        );

        datas.put(
                "7L",
                new DataStruct(
                        "Code Finess de l'organisme agréé",
                        9,
                        9)
        );

        datas.put(
                "7M",
                new DataStruct(
                        "Identification du médecin",
                        0,
                        64)
        );

        datas.put(
                "7N",
                new DataStruct(
                        "Lieu de validation du certificatde décès",
                        0,
                        128)
        );

        datas.put(
                "7O",
                new DataStruct(
                        "Certificat de décès supplémentaire",
                        1,
                        1)
        );

        datas.put(
                "80",
                new DataStruct(
                        "Nom",
                        0,
                        38)
        );

        datas.put(
                "81",
                new DataStruct(
                        "Prénoms",
                        0,
                        60)
        );

        datas.put(
                "82",
                new DataStruct(
                        "Numéro de carte",
                        0,
                        20)
        );

        datas.put(
                "83",
                new DataStruct(
                        "Organisme de tutelle",
                        0,
                        40)
        );

        datas.put(
                "84",
                new DataStruct(
                        "Profession",
                        0,
                        40)
        );

        datas.put(
                "90",
                new DataStruct(
                        "Identité de l'huissier de justice",
                        0,
                        38)
        );

        datas.put(
                "91",
                new DataStruct(
                        "Identité ou raison sociale du demandeur",
                        0,
                        38)
        );

        datas.put(
                "92",
                new DataStruct(
                        "Identité ou raison sociale du destinataire",
                        0,
                        38)
        );

        datas.put(
                "93",
                new DataStruct(
                        "Identité ou raison sociale de tiers concerné",
                        0,
                        38)
        );

        datas.put(
                "94",
                new DataStruct(
                        "Intitulé de l'acte",
                        0,
                        38)
        );

        datas.put(
                "95",
                new DataStruct(
                        "Numéro de l'acte",
                        0,
                        18)
        );

        datas.put(
                "96",
                new DataStruct(
                        "Date de signature de l'acte",
                        8,
                        8)
        );

        datas.put(
                "A0",
                new DataStruct(
                        "Pays ayant émis l’immatriculation du véhicule.",
                        2,
                        2)
        );

        datas.put(
                "A1",
                new DataStruct(
                        "Immatriculation du véhicule",
                        0,
                        17)
        );

        datas.put(
                "A2",
                new DataStruct(
                        "Marque du véhicule.",
                        0,
                        17)
        );

        datas.put(
                "A3",
                new DataStruct(
                        "Nom commercial du véhicule",
                        0,
                        17)
        );

        datas.put(
                "A4",
                new DataStruct(
                        "Numéro de série du véhicule (VIN).",
                        17,
                        17)
        );

        datas.put(
                "A5",
                new DataStruct(
                        "Catégorie du véhicule.",
                        1,
                        1)
        );

        datas.put(
                "A6",
                new DataStruct(
                        "Carburant",
                        2,
                        2)
        );

        datas.put(
                "A7",
                new DataStruct(
                        "Taux d’émission de CO2 du véhicule (en g/km).",
                        3,
                        3)
        );

        datas.put(
                "A8",
                new DataStruct(
                        "Indication de la classe environnementale de réception CE.",
                        0,
                        12)
        );

        datas.put(
                "A9",
                new DataStruct(
                        "Classe d’émission polluante.",
                        3,
                        3)
        );

        datas.put(
                "AA",
                new DataStruct(
                        "Date de première immatriculation du véhicule.",
                        8,
                        8)
        );

        datas.put(
                "AB",
                new DataStruct(
                        "Type de lettre",
                        0,
                        8)
        );

        datas.put(
                "AC",
                new DataStruct(
                        "N° Dossier",
                        0,
                        19)
        );

        datas.put(
                "AD",
                new DataStruct(
                        "Date Infraction",
                        4,
                        4)
        );

        datas.put(
                "AE",
                new DataStruct(
                        "Heure de l’infraction",
                        4,
                        4)
        );

        datas.put(
                "AF",
                new DataStruct(
                        "Nombre de points  retirés lors de l’infraction",
                        1,
                        1)
        );

        datas.put(
                "AG",
                new DataStruct(
                        "Solde de points",
                        1,
                        1)
        );

        datas.put(
                "AH",
                new DataStruct(
                        "Numéro de la carte",
                        0,
                        30)
        );

        datas.put(
                "AI",
                new DataStruct(
                        "Date d’expiration initiale",
                        8,
                        8)
        );

        datas.put(
                "AJ",
                new DataStruct(
                        "Numéro EVTC",
                        13,
                        13)
        );

        datas.put(
                "AK",
                new DataStruct(
                        "Numéro de macaron",
                        7,
                        7)
        );

        datas.put(
                "AL",
                new DataStruct(
                        "Numéro de la carte",
                        11,
                        11)
        );

        datas.put(
                "AM",
                new DataStruct(
                        "Motif de sur-classement",
                        0,
                        5)
        );

        datas.put(
                "AN",
                new DataStruct(
                        "Kilométrage",
                        8,
                        8)
        );

        datas.put(
                "B0",
                new DataStruct(
                        "Liste des prénoms",
                        0,
                        60)
        );

        datas.put(
                "B1",
                new DataStruct(
                        "Prénom",
                        0,
                        20)
        );

        datas.put(
                "B2",
                new DataStruct(
                        "Nom patronymique",
                        0,
                        38)
        );

        datas.put(
                "B3",
                new DataStruct(
                        "Nom d'usage",
                        0,
                        38)
        );

        datas.put(
                "B4",
                new DataStruct(
                        "Nom d'épouse/époux ",
                        0,
                        38)
        );

        datas.put(
                "B5",
                new DataStruct(
                        "Nationalité ",
                        2,
                        2)
        );

        datas.put(
                "B6",
                new DataStruct(
                        "Genre ",
                        1,
                        1)
        );

        datas.put(
                "B7",
                new DataStruct(
                        "Date de naissance ",
                        8,
                        8)
        );

        datas.put(
                "B8",
                new DataStruct(
                        "Lieu de naissance ",
                        0,
                        32)
        );

        datas.put(
                "B9",
                new DataStruct(
                        "Pays de naissance ",
                        2,
                        2)
        );

        datas.put(
                "BA",
                new DataStruct(
                        "Mention obtenue ",
                        1,
                        1)
        );

        datas.put(
                "BB",
                new DataStruct(
                        "Numéro ou code d'identification de l'étudiant ",
                        0,
                        50)
        );

        datas.put(
                "BC",
                new DataStruct(
                        "Numéro du diplôme ",
                        0,
                        20)
        );

        datas.put(
                "BD",
                new DataStruct(
                        "Niveau du diplôme selon la classification CEC ",
                        1,
                        1)
        );

        datas.put(
                "BE",
                new DataStruct(
                        "Crédits ECTS obtenus ",
                        3,
                        3)
        );

        datas.put(
                "BF",
                new DataStruct(
                        "Année universitaire ",
                        3,
                        3)
        );

        datas.put(
                "BG",
                new DataStruct(
                        "Type de diplôme ",
                        2,
                        2)
        );

        datas.put(
                "BH",
                new DataStruct(
                        "Domaine ",
                        0,
                        30)
        );

        datas.put(
                "BI",
                new DataStruct(
                        "Mention ",
                        0,
                        30)
        );

        datas.put(
                "BJ",
                new DataStruct(
                        "Spécialité ",
                        0,
                        30)
        );

        datas.put(
                "BK",
                new DataStruct(
                        "Numérode l'Attestation de versement dela CVE",
                        14,
                        14)
        );

        datas.put(
                "C0",
                new DataStruct(
                        "Genre du vendeur",
                        1,
                        1)
        );

        datas.put(
                "C1",
                new DataStruct(
                        "Nom patronymique du vendeur",
                        0,
                        38)
        );

        datas.put(
                "C2",
                new DataStruct(
                        "Prénom du vendeur",
                        0,
                        20)
        );

        datas.put(
                "C3",
                new DataStruct(
                        "Date et heure de la cession",
                        12,
                        12)
        );

        datas.put(
                "C4",
                new DataStruct(
                        "Date de la signature du vendeur",
                        8,
                        8)
        );

        datas.put(
                "C5",
                new DataStruct(
                        "Genre de l’acheteur",
                        1,
                        1)
        );

        datas.put(
                "C6",
                new DataStruct(
                        "Nom patronymique de l’acheteur",
                        0,
                        38)
        );

        datas.put(
                "C7",
                new DataStruct(
                        "Prénom de l’acheteur",
                        0,
                        20)
        );

        datas.put(
                "C8",
                new DataStruct(
                        "Ligne 4 de la norme adresse postale du domicile de l’acheteur",
                        0,
                        38)
        );

        datas.put(
                "C9",
                new DataStruct(
                        "Code postal ou code cedex du domicile de l’acheteur",
                        5,
                        5)
        );

        datas.put(
                "CA",
                new DataStruct(
                        "Commune du domicile de l’acheteur",
                        0,
                        45)
        );

        datas.put(
                "CB",
                new DataStruct(
                        "CN° d’enregistrement",
                        10,
                        10)
        );

        datas.put(
                "CC",
                new DataStruct(
                        "Date et heure d'enregistrement dans le SIV",
                        12,
                        12)
        );

    }

    class DataStruct{
        String name;
        Integer minSize = null;
        Integer maxSize = null;

        public DataStruct(String name, Integer minSize, Integer maxSize) {
            this.name = name;
            this.minSize = minSize;
            this.maxSize = maxSize;
        }
    }

}
