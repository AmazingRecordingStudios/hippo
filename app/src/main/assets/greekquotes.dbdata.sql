INSERT INTO "android_metadata" ("locale") VALUES ('en_US');
INSERT INTO "translation_languages" ("_id","LanguageName") VALUES (1,'English');
INSERT INTO "translation_languages" ("_id","LanguageName") VALUES (2,'Italian');

INSERT INTO "credits" ("credit") VALUES ('Laboratorio di Cultura Digitale, Dipartimento di Lettere e Filosofia, Università di Pisa, che ha ospitato il progetto fino a Gennaio 2020');
INSERT INTO "credits" ("credit") VALUES ('Wikipedia, List of Greek phrases, https://en.wikipedia.org/wiki/List_of_Greek_phrases');
INSERT INTO "credits" ("credit") VALUES ('Francese, Christopher. Greek Core Vocabulary. Dickinson College Commentaries (2014). http://dcc.dickinson.edu/greek-core-list');
INSERT INTO "credits" ("credit") VALUES ('Revithiadou, A. (2018) Ancient Greek pitch accent');
INSERT INTO "credits" ("credit") VALUES ('Poesia laina, testi greci interattivi http://www.poesialatina.it/_ns/Greek/tt2/p/Elenco.html');
INSERT INTO "credits" ("credit") VALUES ('http://homermultitext.blogspot.com/2013/06/iliad-8-scholia-on-mythological.html');
INSERT INTO "credits" ("credit") VALUES ('https://latinygriegojanda.files.wordpress.com/2012/06/orfeo-y-euridice.pdf');
INSERT INTO "credits" ("credit") VALUES ('https://www.cornellcollege.edu/classical_studies/ariadne/audio/index.shtml');
INSERT INTO "credits" ("credit") VALUES ('https://soundcloud.com/archaeologymag/what-ancient-greek-music');
INSERT INTO "credits" ("credit") VALUES ('https://sites.google.com/site/materialesathenaze/ho-dikaiopolis');
INSERT INTO "credits" ("credit") VALUES ('http://aliso.pntic.mec.es/agalle17/trabajo_final/cap4.html');
INSERT INTO "credits" ("credit") VALUES ('https://librivox.org/the-odyssey-book-6-by-homer/');
INSERT INTO "credits" ("credit") VALUES ('https://www.theanimalspot.com/hippopotamus/');
INSERT INTO "credits" ("credit") VALUES ('Major, Wilfred E. (2008). It’s Not the Size, It’s the Frequency: The Value of Using a Core Vocabulary in Beginning and Intermediate Greek. CPL Online, 4.1, 1-24.  https://camws.org/cpl/cplonline/files/Majorcplonline.pdf');
INSERT INTO "credits" ("credit") VALUES ('The Android robot is reproduced or modified from work created and shared by Google and used according to terms described in the Creative Commons 3.0 Attribution License. https://creativecommons.org/licenses/by/3.0/');

INSERT INTO "app_notes" ("title","note") VALUES ('about','Hippopotamuses are located in central and southern Africa, south of the Sahara desert and near the Nile River.<br/><br/><b>Diet</b><br/>They are vegetarians and mostly eat grass. They may travel long distances during the night from their water source to reach the areas where they graze. They also sometimes eat fruit that has fallen off of trees or crops grown by local people.');

INSERT INTO "news" ("title","link") VALUES ('Pablo Escobar''s hippos keep multiplying and Colombia doesn''t know how to stop it','https://www.cbsnews.com/news/pablo-escobars-hippos-keep-multiplying-and-colombia-doesnt-know-how-to-stop-it/');

INSERT INTO "schermate" ("_id","description") VALUES (1,'Disyllabic paroxytone words');
INSERT INTO "schermate_descriptions_translations" ("schermata_id","language_id","description") VALUES (1,2,'parossìtone due sillabe');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (3,'τόπος');
INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (4,'φίλος','filosf.ogg');
INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (5,'λόγος','logosf.ogg');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (3,2,'Luogo');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (4,2,'Caro, amico');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (5,2,'parola, linguaggio, discorso; pensiero, ragione, conto');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (1,3,1);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (1,4,2);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (1,5,3);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (1,1,'Place, friend, word.');

INSERT INTO "schermate" ("_id","description") VALUES (2,'Trisyllabic Proparoxytone words');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (2,1,'Man, messenger.');
INSERT INTO "schermate_descriptions_translations" ("schermata_id","language_id","description") VALUES (2,2,'proparossìtone tre sillabee');
INSERT INTO "greek_quotes" ("_id","quoteText","phoneticTranscription","audioFileName") VALUES (6,'ἄνθρωπος','ántʰrɔɔpós','anthrwpf.ogg');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (7,'ἄγγελος');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (6,2,'Uomo');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (7,2,'Messaggero');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (2,6,1);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (2,7,2);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (3,'Fun Fact','Plutarch, De garrulitate, 17');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (3,1,'if');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (8,'α<CASE>ἴ</CASE>κ<CASE>α</CASE>');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (8,2,'se (ipotetico) (solo in dialetto dorico)');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (3,8);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (3,1,'αἴκα / αἴκε / αἴ κα / αἴ κε dor. / hom. eἴκε for εἰ+ἄν > ἐάν, ἄν');
INSERT INTO "easter_egg_comments" ("schermata_id","language_id","eeComment") VALUES (3,1,'Plutarch reports that Phillip II of Macedon sent word to the Spartans, saying that "if I should invade Laconia, I shall drive you out." The Spartans laconically responded with "if."');

INSERT INTO "schermate" ("_id","description") VALUES (4,'1st declension, pure -ᾱ');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (4,1,'Region, fate, day.');
INSERT INTO "schermate_descriptions_translations" ("schermata_id","language_id","description") VALUES (4,2,'I declinazione in α puro');
INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (9,'χώρα','xwraf.ogg');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (10,'μοῖρα');
INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (11,'ἡμέρα','hhmeraf.ogg');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (9,2,'regione');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (10,2,'destino');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (11,2,'giorno');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (4,11,1);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (4,9,1);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (4,10,1);

INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (13,'Οὐραν<CASE>ὸς</CASE> πρῶτ<CASE>ος</CASE> τοῦ παντὸς ἐδυνάστευσε κόσμου.');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (62,'Οὐραν<CASE>ὸς</CASE> πρῶτ<CASE>ος</CASE>');
INSERT INTO "schermate" ("_id","full_quote_id","short_quote_id","description","author_ref") VALUES (5,13,62,'Incipits','Apollod. 1.1.1');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (5,1,'Uranus, Sky, was the first lord of the whole universe.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (13,2,'Urano fù il primo signore di tutto l''universo.');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (5,1,'-ὸς: NOM sg masc; πρῶτος: "per primo", NOM sg');

INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (71,'τ<CASE>οῦ</CASE> παντ<CASE>ὸς</CASE> κόσμ<CASE>ου</CASE>');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (72,'Οὐρανὸς πρῶτος τ<CASE>οῦ</CASE> παντ<CASE>ὸς</CASE> ἐδυνάστευσε κόσμ<CASE>ου</CASE>.');
INSERT INTO "schermate" ("_id","short_quote_id","full_quote_id","description","author_ref") VALUES (61,71,72,'παντὸς κόσμου','Apollod. 1.1.1');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (61,1,'Uranus, Sky, was the first lord of the whole universe.');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (61,1,'-οῦ, ὸς: GEN sg (m, II decl.); ἐδυνάστευσε + GEN: was lord over');

INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (73,'εἰς Τάρταρ<CASE>ον</CASE>');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (14,'τούτους .. εἰς Τάρταρον ἔρριψε （τόπος δὲ οὗτος ἐρεβώδης ἐστὶν ἐν Ἅιδου, τοσοῦτον ἀπὸ γῆς ἔχων διάστημα ὅσον ἀπ᾿ οὐρανοῦ γῆ.)');
INSERT INTO "schermate" ("_id","short_quote_id","full_quote_id","description","author_ref") VALUES (6,73,14,'εἰς Τάρταρον','Apollod. 1.1.2');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (6,1,'he threw them into Tartarus - this is a dark place in the Ade, as far from Earth as Earth is from heaven (lit.: having as much distance from Earth as Earth from heaven)');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (6,1,'εἰς Τάρταρον, εἰς + ACC(sg, II decl.)');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (7,'The start of Time','Apollod. 1.1.3');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (7,1,'And again he (Uranus, the sky) begat children by Earth, the Titans as they are named: Ocean, Coeus, Hyperion, Crius, Iapetus, and, youngest of all, Cronus (Time)');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (15,'τεκνοῖ δὲ αὖθις ἐκ Γῆς παῖδας μὲν τοὺς Τιτᾶνας προσαγορευθέντας, Ὠκεανὸν Κοῖον Ὑπερίονα Κρεῖον Ἰαπετὸν καὶ νεώτατον ἁπάντων Κρόνον');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (15,2,'And again he (Uranus, the sky) begat children by Earth, the Titans as they are named: Ocean, Coeus, Hyperion, Crius, Iapetus, and, youngest of all, Cronus (Time)');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (7,15);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (8,'Zeus, Pluto, Poseidon','Apollod. I.2.1');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (8,1,'And the Cyclopes then gave Zeus thunder and lightning and a thunderbolt, and on Pluto they bestowed a helmet and on Poseidon a trident.');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (16,' καὶ Κύκλωπες τότε Διὶ μὲν διδόασι βροντὴν καὶ ἀστραπὴν καὶ κεραυνόν, Πλούτωνι δὲ κυνέην, Ποσειδῶνι δὲ τρίαιναν');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (16,1,'And the Cyclopes then gave Zeus thunder and lightning and a thunderbolt, and on Pluto they bestowed a helmet and on Poseidon a trident.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (8,16);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (9,'Δίκην, Εἰρήνην, Εὐνομίαν','Apollod. I.3.1');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (9,1,'Peace, Order, Justice, and the Fates');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (17,'Εἰρήνην Εὐνομίαν Δίκην, μοίρας');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (17,2,'Irene, la Pace, Eunomia, l''Ordine, Dike, la Giustizia, e le Moire');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (17,1,'Peace, Order, Justice, and the Fates');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (9,17);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (9,1,'Εἰρήνην Εὐνομίαν Δίκην: ACC sg; μοίρας: ACC pl (f)');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (10,'The North Wind and the Sun','Aesop');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (10,1,'The North Wind and the Sun were disputing which was the stronger (on strenght).');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (18,'Βορέας καὶ Ἥλιος περὶ δυνάμεως ἤριζον·');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (18,2,'Borea e il Sole litigavano su chi fosse il più forte (sulla forza).');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (18,1,'The North Wind and the Sun were disputing which was the stronger (on strenght).');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (10,18);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (10,1,'ἤριζον: imperf ind act, 3rd pl (ἐρίζω)');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (22,'The North Wind and the Sun (2)','Aesop');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (22,1,'It was resolved that vicτory would be given to the one who succeeded in making a traveler take off his cloak.');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (33,'ἔδοξε δὲ αὐτοῖς ἐκείνῳ τὴν νίκην ἀπονεῖμαι, ὃς ἂν αὐτῶν ἄνθρωπον ὁδοιπόρον ἐκδύσῃ.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (33,2,'Decisero di assegnare la vittoria a chi di loro avesse spogliato un viandante.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (33,1,'It was resolved that the one who succeeded in making a traveler take his cloak off would win.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (22,33);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (22,1,'ἔδοξε: aor ind act, 3rd sg');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (23,'The North Wind and the Sun (3)','Aesop');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (23,1,'Then the North Wind started blowing hard; and the more he blew the more closely did the traveler fold his cloak around himself.');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (28,'καὶ ὁ Βορέας ἀρξάμενος σφοδρὸς ἦν· τοῦ δὲ ἀνθρώπου ἀντεχομένου τῆς ἐσθῆτος μᾶλλον ἐπέκειτο.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (28,2,'Boreas all''inzio era impetuoso; e siccome l''uomo stava attaccato al vestito, soffiava di più.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (28,1,'Then the North Wind started blowing hard; and the more he blew the more closely did the traveler fold his cloak around himself.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (23,28);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (23,1,'ὁ Βορέας ἀρξάμενος σφοδρὸς ἦν: ..');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (24,'The North Wind and the Sun (4)','Aesop');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (24,1,'Tormented even more by the cold, he put more clothes on.');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (29,'ὁ δὲ ὑπὸ τοῦ ψύχους καταπονούμενος ἔτι μᾶλλον καὶ περιττοτέραν ἐσθῆτα προσελάμβανεν,');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (29,2,'E quello, tormentato ancora di più dal freddo si aggiunse anche un altro vestito,');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (24,29);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (24,1,'καταπονούμενος: ..; προσελάμβανεν: ..');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (25,'The North Wind and the Sun (5)','Aesop');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (25,1,'and at last the North Wind grew quite weary, handing it over to the Sun.');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (30,'ἕως ἀποκαμὼν ὁ Βορέας τῷ Ἡλίῳ μεταπαρέδωκε.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (30,2,'finché Boreas, stancatosi, cedette (l''uomo) ad Elios.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (30,1,'and at last the North Wind grew quite weary, handing it over to the Sun.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (25,30);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (25,1,'ἀποκαμὼν: part sg aor act, NOM sg (m); μεταπαρέδωκε: 3rd sg aor ind act');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (26,'The North Wind and the Sun (6)','Aesop');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (26,1,'then he (the Sun) first started shining mildly;');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (31,'κἀκεῖνος τὸ μὲν πρῶτον μετρίως προσέλαμψε·');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (26,31);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (27,'The North Wind and the Sun (7)','Aesop');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (27,1,'as the traveler took off the extra clothes, he sent more intense heat.');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (36,'τοῦ δὲ ἀνθρώπου τὰ περισσὰ τῶν ἱματίων ἀποτιθεμένου σφοδρότερον τὸ καῦμα ἐπέτεινε,');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (27,36);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (31,'The North Wind and the Sun (8)','Aesop');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (31,1,'In the end, when the traveler couldn''t stand the heat, since there was a river nearby, he stripped off his clothes and went to take a bath.');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (37,'μέχρις οὗ πρὸς τὴν ἀλέαν ἀντέχειν μὴ δυνάμενος ἀποδυσάμενος ποταμοῦ παραῤῥέοντος ἐπὶ λουτρὸν ἀπῄει.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (31,37);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (32,'The North Wind and the Sun (9)','Aesop');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (32,1,'The story shows that persuasion is often more effective than violence.');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (38,'ὁ λόγος δηλοῖ, ὅτι πολλάκις τὸ πείθειν τοῦ βιάζεσθαι ἀνυστικώτερόν ἐστι.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (32,38);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (11,'Eureka','Archimedes in Plutarch''s Moralia 1094c');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (11,1,'I''ve found it');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (19,'εὕρηκα');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (19,2,'ho trovato.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (11,19);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (11,2,'perfetto.. da εὑρίσκω, pf.');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (11,1,'εὕρηκα: verb 1st sg perf ind act ionic no_redupl, from εὑρίσκω: "to find"');

INSERT INTO "greek_quotes" ("_id","quoteText","phoneticTranscription") VALUES (12,'τις ἀθανάτ<CASE>ων</CASE>','tis atʰanátɔɔn');
INSERT INTO "schermate" ("_id","short_quote_id","description","author_ref") VALUES (12,12,'GEN pl','Hom. Iliad 6.128');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (12,1,'one of the immortals');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (12,2,'uno degli immortali');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (12,1,'-ων: GEN pl; τις: "some one", NOM sg; ἀθανάτων: "of the immortals"');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (13,'Thyrants','Diogenes Laertius, Lives of Eminent Philosophers 1.1.36');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (13,1,'To the question what was the strangest thing he had ever seen, his answer was, "An aged tyrant."');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (20,'τί δὲ καινὸν εἴη τεθεαμένος ἔφη: "γέροντα τύραννον."');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (20,1,'To the question what was the strangest thing he had ever seen, his answer was, "An aged tyrant."');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (13,20);

INSERT INTO "playlists" ("_id","description","disabled") VALUES (8,'Test playlist',1);
INSERT INTO "schermate" ("_id","description") VALUES (14,'audio test');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (14,4);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (14,5);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (14,6);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (14,9);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (14,11);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (15,'In the nether world','Apollod. 1.2.1');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (21,'καθείρξαντες αὐτοὺς ἐν τῷ Ταρτάρῳ');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (21,1,'shut them up in Tartarus (in the nether world)');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (15,1,'shut them up in Tartarus (in the nether world)');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (15,21);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (15,1,'ἐν τῷ Ταρτάρῳ: DAT sg (m)..');

INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (75,'ἐν οὐραν<CASE>ῷ</CASE> .. ἐν θαλάσσ<CASE>ῃ</CASE>');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (22,'καὶ λαγχάνει Ζεὺς μὲν τὴν ἐν οὐραν<CASE>ῷ</CASE> δυναστείαν, Ποσειδῶν δὲ τὴν ἐν θαλάσσ<CASE>ῃ</CASE>, Πλούτων δὲ τὴν ἐν Ἅιδου.');
INSERT INTO "schermate" ("_id","short_quote_id","full_quote_id","description","author_ref") VALUES (16,75,22,'Heavens, sea and Hades','Apollod. 1.2.1');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (16,1,'and to Zeus was allotted the dominion in the sky, to Poseidon the dominion in the sea, and to Pluto the dominion in Hades');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (16,1,'-ῃ: DAT sg fem; ἐν οὐρανῷ, ἐν θαλάσσῃ: "in the sky, in the sea", DAT sg; ἐν Ἅιδου: shorthand for ἐν οἴκ<CASE>ῳ</CASE> Ἅιδου, "in Hades'' house"');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (17,'Atlas','Apollod. 1.2.3');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (23,'Ἄτλας, ὃς ἔχει τοῖς ὤμοις τ<CASE>ὸν</CASE> οὐραν<CASE>όν</CASE>');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (23,2,'Atlante, che porta il cielo sulle spalle');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (17,1,'Atlas, who carries the sky on his shoulders');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (17,23);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (17,1,'-όν: ACC sg masc; τὸν οὐρανόν: "the sky"; -οις: DAT pl masc; τοῖς ὤμοις: "on the shoulders"');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (18,'ἄνεμοι καὶ ἄστρα','Apollod. 1.2.4');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (24,'Ἠοῦς δὲ καὶ Ἀστραίου ἄνεμ<CASE>οι</CASE> καὶ ἄστρ<CASE>α</CASE>');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (24,2,'e dall''Aurora e da Astreo nacquero i venti e le stelle');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (18,1,'and to Dawn and Astraeus were born winds and stars');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (18,24);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (18,1,'ἄνεμοι καὶ ἄστρα: "winds and stars," NOM pl (m, nt)');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (19,'Νίκη Κράτος Ζῆλος Βία.','Apollod. 1.2.4');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (25,'Πάλλαντος δὲ καὶ Στυγὸς Νίκη Κράτος Ζῆλος Βία.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (25,1,'and to Pallas and Styx were born Victory, Dominion, Emulation, and Violence.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (25,2,'e da Pallante e Stige nacquero Nike, la vittoria, Crato, il potere, Zelo, l''ardore, e Bia, la violenza.');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (19,1,'and to Pallas and Styx were born Victory, Dominion, Emulation, and Violence.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (19,25);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (19,1,'Νίκη Κράτος Ζῆλος Βία.: NOM sg (f, nt, m, f)');

INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (76,'πολλ<CASE>αῖς</CASE> γυναιξ<CASE>ίν</CASE>');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (26,'Ζεὺς δὲ γαμεῖ μὲν Ἥραν .. μίγνυται δὲ πολλ<CASE>αῖς</CASE> θνητ<CASE>αῖς</CASE> τε καὶ ἀθανάτ<CASE>οις</CASE> γυναιξ<CASE>ίν</CASE>.');
INSERT INTO "schermate" ("_id","short_quote_id","full_quote_id","description","author_ref") VALUES (20,76,26,'Ζεὺς','Apollod. 1.3.1');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (26,2,'Zeus sposa Era .. ma si unisce a molte donne, mortali e immortali');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (20,1,'Zeus wedded Hera .. but he had intercourse with many women, both mortals and immortals.');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (20,1,'-αῖς: DAT pl fem; πολλαῖς: "with many"; γυναιξίν: "with women", DAT pl; θνηταῖς τε καὶ ἀθανάτοις: "with both mortals and immortals", DAT pl.');

INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (27,'ἐκ δὲ Μνημοσύν<CASE>ης</CASE> μούσας');
INSERT INTO "schermate" ("_id","short_quote_id","description","author_ref") VALUES (21,27,'μούσας','Apollod. 1.3.1');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (21,1,'and by Memory he got the Muses');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (21,1,'-ης: GEN sg fem; ἐκ Μνημοσύνης: GEN sg, μούσας: ACC pl');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (27,2,'da Mnemosine gli nascono le Muse');

INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (74,'ἐκ δὲ Μνημοσύνης μούσ<CASE>ας</CASE>');
INSERT INTO "schermate" ("_id","short_quote_id","description","author_ref") VALUES (62,74,'μούσας','Apollod. 1.3.1');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (62,1,'and by Memory he got the Muses');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (62,1,'-ας: ACC pl fem');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (28,'The Spartans','Plutarch, De garrulitate, 17');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (34,'γράψαντος αὐτοῖς τοῦ Φιλίππου ‘ἂν ἐμβάλω εἰς τὴν Λακωνικήν, ἀναστάτους ὑμᾶς ποιήσω, ’ ἀντέγραψαν ‘αἴκα.’');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (34,1,'when Philip wrote to them, ‘If I invade Laconia, I shall turn you out,’ they wrote back, ‘If.’');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (28,1,'when Philip wrote to them, ‘If I invade Laconia, I shall turn you out,’ they wrote back, ‘If.’');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (28,34);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (28,1,'');

INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (77,'ᾁδων ἐκίνει λίθ<CASE>ους</CASE> τε καὶ δένδρ<CASE>α</CASE>.');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (35,'Ὀρφεὺς ὁ ἀσκήσας κιθαρῳδίαν, ὃς ᾁδων ἐκίνει λίθ<CASE>ους</CASE> τε καὶ δένδρ<CASE>α</CASE>.');
INSERT INTO "schermate" ("_id","short_quote_id","full_quote_id","description","author_ref") VALUES (29,77,35,'Ὀρφεὺς','Apollod. 1.3.2');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (29,1,'Orpheus, who practised minstrelsy and by singing moved stones and trees.');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (29,1,'λίθους τε καὶ δένδρα: "stones and trees" ACC pl (f, nt II decl.); ᾁδων: "by singing", NOM sg masc part pres act contr; ἐκίνει: "moved", 3rd sg imperf ind act attic epic contr');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (33,'Another one bites the dust','Homer, Iliad, XI.749, XIX.61, XXII.17, XXIV.738');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (39,'ὀδὰξ ἕλον οὖδας.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (33,39);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (33,1,'with their teeth have they bitten the dust.');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (33,1,'ὀδὰξ: adv. "(biting) with the teeth"; ἕλον (αἱρέω): "grasp", aor.2 homeric, 3rd pl ind act; οὖδας: "surface of the earth, ground" sg neut epic indeclform');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (34,'The Memorable thoughts of Socrates','Xenophon, Memorabilia 1.1.1');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (40,'ἀδικεῖ Σωκράτης οὓς μὲν ἡ πόλις νομίζει θεοὺς οὐ νομίζων, ἕτερα δὲ καινὰ δαιμόνια εἰσφέρων: ἀδικεῖ δὲ καὶ τοὺς νέους διαφθείρων.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (34,40);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (34,1,'Socrates is guilty of rejecting the gods acknowledged by the state, and of introducing new strange deities: he is also guilty of corrupting the youth.');

INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (66,'δῖ<CASE>ος</CASE> Ὀδυσσεὺς','Od.6.1-diosodisseus.ogg');
INSERT INTO "schermate" ("_id","short_quote_id","description","author_ref") VALUES (56,66,'The divine Odysseus','Hom. Od. 6.1');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (56,1,'divine Odysseus');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (56,1,'-ος: NOM sg');

INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (69,'ὣς ὁ μὲν ἔνθα καθεῦδε','Od.6.1-os-o-men-enta-kataude.ogg');
INSERT INTO "schermate" ("_id","short_quote_id","author_ref") VALUES (59,69,'Hom. Od. 6.1');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (59,1,'So there he slept');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (59,1,'-ὣς: "so"; ἔνθα: "there"; ὁ: "he", NOM sg; καθεῦδε: "slept", 3rd sg imperf ind act.');

INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (70,'ὕπν<CASE>ῳ</CASE> καὶ καμάτ<CASE>ῳ</CASE> ἀρημένος','Od.6.2-upnw.ogg');
INSERT INTO "schermate" ("_id","short_quote_id","author_ref") VALUES (60,70,'Hom. Od. 6.2');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (60,1,'overcome by sleep and toil');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (60,1,'-ῳ: DAT sg; ὕπνῳ: "by sleep"; καὶ: "and"; καμάτῳ: "by toil"; ἀρημένος: "afflicted", NOM sg masc part perf mp epic ionic, from ἀράω');

INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (67,'ἀνδρ<CASE>ῶν</CASE>','Od.6.5-andrwn.ogg');
INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (68,'ὑπερηνορεόντ<CASE>ων</CASE>','Od.6.5-uper-enore-ontwn.ogg');
INSERT INTO "schermate" ("_id","short_quote_id","full_quote_id","description","author_ref") VALUES (57,67,68,'Arrogant men','Hom. Od. 6.5');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (57,67);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (57,1,'of arrogant men');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (57,1,'-ῶν: GEN pl; ἀνδρῶν: "men", from ἀνήρ; ὑπερηνορεόντων: exceedingly manly:—but always in bad sense, overbearing, overweening, from ὑπερηνορέων (NOM sg), part. as adjective from noun ὑπερήνωρ, ὑπερ + ηνορ, "overbearing, overweening".');

INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (42,'οἵ σφε<CASE>ας</CASE> σινέσκοντο','Od.6.6-oispheas.ogg');
INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (41,'ἀνδρῶν ὑπερηνορεόντων, οἵ σφε<CASE>ας</CASE> σινέσκοντο','');
INSERT INTO "schermate" ("_id","short_quote_id","full_quote_id","description","author_ref") VALUES (36,42,41,'Plunderers','Hom. Od. 6.6');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (36,1,'of arrogant men, that plundered them');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (36,1,'-ας: ACC pl; σφεας: them.');

INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (43,'ἑκὰς ἀνδρ<CASE>ῶν</CASE> ἀλφηστά<CASE>ων</CASE>','Od.6.8-ekasandron.ogg');
INSERT INTO "schermate" ("_id","short_quote_id","description","author_ref") VALUES (37,43,'far from men that live by toil','Hom. Od. 6.8');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (37,1,'far from men that live by toil');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (37,1,'-ων: GEN pl; <br/>ἑκὰς + GEN: "far from";<br/>ἀνδρῶν: "men";<br/>ἀλφηστάων: industrious, enterprising. Originally might have literally meant "one who eats bread", a composite from ἄλφι (flour) and the suffix -ησ-τής, "eater", and by extension meant "one who earns his daily bread, his food", therefore "the worker" and consequently "the active, the industrious"');

INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (44,'ἀμφὶ δὲ τεῖχος ἔλασσε πόλει','Od.6.9-anphideteikos.ogg');
INSERT INTO "schermate" ("_id","full_quote_id","description","author_ref") VALUES (38,44,'The wall','Hom. Od. 6.9');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (38,1,'and he surrounded the city with a wall');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (38,1,'ἔλασσε: "he layed out", 3rd sg aor ind act epic; τεῖχος: "a wall", ACC sg neut; ἀμφὶ: "around" πόλει: "the city", DAT sg fem epic doric ionic;');

INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (45,'καὶ ἐδείματο οἴκους','Od.6.9-kaiedeimato.ogg');
INSERT INTO "schermate" ("_id","short_quote_id","description","author_ref") VALUES (39,45,'οἴκους','Hom. Od. 6.9');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (39,1,'and he built houses');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (39,1,'ἐδείματο: "he built", 3rd sg aor ind mid, from δέμω; οἴκους: "houses", ACC m pl, from οἶκος');

INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (46,'καὶ νηοὺς ποίησε θεῶν','Od.6.10-kaineus.ogg');
INSERT INTO "schermate" ("_id","full_quote_id","description","author_ref") VALUES (40,46,'Temples (νηοὺς)','Hom. Od. 6.10');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (40,1,'and he made temples for the gods');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (40,1,'νηούς (attic: νᾱούς): "temples", ACC pl masc epic ionic from ναός; ποίησε: "he made", 3rd sg aor ind act homeric ionic, from ποιέω');

INSERT INTO "schermate" ("_id","title","author_ref") VALUES (41,'Spell','Hom. Od. 6.13, 6.24, 6.41');
INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (47,'γλαυκῶπις Ἀθήνη','Od.6.13-glaukopis.ogg');
INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (48,'γλαυκῶπις Ἀθήνη','Od.6.24-glaukopis.ogg');
INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (49,'γλαυκῶπις Ἀθήνη','Od.6.41-glaukopis.ogg');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (41,47);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (41,48);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (41,49);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (41,1,'gleaming-eyed Athena');

INSERT INTO "schermate" ("_id","short_quote_id","description","author_ref") VALUES (58,47,'Gleaming eyes','Hom. Od. 6.13');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (58,1,'gleaming-eyed Athena');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (58,1,'γλαυκῶπις: "with gleaming eyes", NOM sg fem, from γλαυκός "gleaming" and ὤψ "eye"');

INSERT INTO "greek_quotes" ("_id","audioFileName","quoteText") VALUES (50,'Od.6.41-6-olimpon.ogg','ἡ μὲν ἄρ᾽ ὣς εἰποῦσ᾽ ἀπέβη γλαυκῶπις Ἀθήνη </BR> Οὔλυμπόνδ᾽, ὅθι φασὶ θεῶν ἕδος ἀσφαλὲς αἰεὶ </BR> ἔμμεναι. οὔτ᾽ ἀνέμοισι τινάσσεται οὔτε ποτ᾽ ὄμβρῳ </BR> δεύεται οὔτε χιὼν ἐπιπίλναται, ἀλλὰ μάλ᾽ αἴθρη </BR> πέπταται ἀνέφελος, λευκὴ δ᾽ ἐπιδέδρομεν αἴγλη: </BR> τῷ ἔνι τέρπονται μάκαρες θεοὶ ἤματα πάντα.');
INSERT INTO "schermate" ("_id","full_quote_id","description","author_ref") VALUES (42,50,'Olympus','Hom. Od. 6.41-46');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (42,1,'Athena went away to Olympus, which they say is the everlasting home of the gods. Here no wind beats roughly, and neither rain nor snow can fall; but it abides in everlasting sunshine and in a great peacefulness of light, wherein the blessed gods are illumined for ever and ever.');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (43,'Vultures eat his heart in Hades','Apollod. 1.4.1');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (51,'γῦπες γὰρ αὐτοῦ τὴν καρδίαν ἐν Ἅιδου ἐσθίουσιν.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (43,51);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (43,1,'for vultures eat his heart in Hades');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (44,'','Apollod. 1.4.2');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (52,'ἐκτεμὼν τὸ δέρμα');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (44,52);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (44,1,'stripping off (his) skin');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (45,'','Iliad 1.4');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (53,'αὐτοὺς δὲ ἑλώρια τεῦχε κύνεσσιν οἰωνοῖσί τε πᾶσι');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (45,53);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (45,1,'made them prey for all dogs and birds');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (46,'','Iliad 1.5');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (54,'Διὸς δ᾽ ἐτελείετο βουλή');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (46,54);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (46,1,'and so was the will of Zeus fulfilled');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (47,'','Iliad 1.18');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (55,'θεοὶ .. Ὀλύμπια δώματ᾽ ἔχοντες');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (47,55);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (47,1,'the gods who have houses in mount Olympus');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (48,'','Iliad 1.40');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (56,'πίονα μηρί᾽ ἔκηα ταύρων ἠδ᾽ αἰγῶν');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (48,56);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (48,1,'burned fat thigh-pieces of bulls and goats');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (49,'','Iliad 1.41');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (57,'τὸ δέ μοι κρήηνον ἐέλδωρ:');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (49,57);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (49,1,'fulfill this prayer for me');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (50,'','Iliad 1.42');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (58,'τίσειαν Δαναοὶ ἐμὰ δάκρυα σοῖσι βέλεσσιν.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (50,58);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (50,1,'let the Greeks pay for my tears by your arrows');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (51,'','Iliad 1.49');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (59,'δεινὴ δὲ κλαγγὴ γένετ᾽ ἀργυρέοιο βιοῖο:');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (51,59);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (51,1,'a terrible twang came from the silver bow.');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (51,1,'γένετ᾽: (γίγνομαι) 3rd sg aor ind mid homeric ionic unaugmented');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (52,'','Iliad 1.30');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (60,'ἡμετέρ<CASE>ῳ</CASE> ἐνὶ οἴκ<CASE>ῳ</CASE>');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (52,60);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (52,1,'in our house.');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (52,1,'ἐνὶ οἴκῳ: ἐν + DAT; ἐνὶ: just a poetic version of ἐν');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (53,'','Iliad 6.500');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (61,'αἳ μὲν ἔτι ζωὸν γόον Ἕκτορα <CASE>ᾧ</CASE> ἐνὶ οἴκ<CASE>ῳ</CASE>');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (53,61);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (53,1,'although still alive, they mourned Hector in his house');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (53,1,'αἳ: "they" (female); ᾧ: "in his", DAT; ζωὸν: adj sg masc ACC');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (54,'the greatest of all gain, the acquisition of a good friend','Xenophon, Memorabilia 1.2.7');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (63,'ἐθαύμαζε δ᾽ εἴ τις ἀρετὴν ἐπαγγελλόμενος ἀργύριον πράττοιτο καὶ μὴ νομίζοι τὸ μέγιστον κέρδος ἕξειν φίλον ἀγαθὸν κτησάμενος');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (54,63);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (54,1,'He wondered, likewise, why a man, who promises to teach virtue, should ask money; as if he believed not the greatest of all gain to consist in the acquisition of a good friend');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (55,'','Xenophon, Memorabilia 1.6.14');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (64,'ἥδομαι φίλοις ἀγαθοῖς');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (55,64);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (55,1,'my delight is to be with good friends');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (55,1,'φίλοις ἀγαθοῖς: DAT pl; ἥδομαι: verb 1st sg pres ind mp');

INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (2,'ἀγαθός');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (2,2,'Buono, nobile');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (2,1,'Good, noble');

INSERT INTO "playlists" ("_id","description") VALUES (0,'Prepositions');
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (0,3);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (0,59);

INSERT INTO "playlists" ("_id","description","disabled") VALUES (1,'Words, declensions',0);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (1,1);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (1,2);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (1,4);

INSERT INTO "playlists" ("_id","description","disabled") VALUES (2,'Famous quotes',0);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (2,3);

INSERT INTO "playlists" ("_id","description") VALUES (3,'Reading list - word series for ear training');
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (3,1);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (3,2);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (3,4);

INSERT INTO "playlists" ("_id","play_order","description") VALUES (4,1,'Micro quotes to learn noun declensions');
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,5);--/Uranus
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,56);--/Dios Odysseus
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,21);--/GEN sg From Memory the Muses
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,61);--/GEN sg, di tutto l'universo
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,6); --/GEN sg ἐν Ἅιδου
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,60);--/DAT sg overcome by sleep and toil
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,53);--/DAT sg in his house
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,52);--/DAT sg in our house
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,16);--/DAT sg, the dominion in the sky, the dominion in the sea
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,17);--/ACC sg carries the sky (on his shoulders)
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,18);--/NOM pl ἄνεμοι καὶ ἄστρα, winds and stars 1.2.4
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,12);--/GEN pl one of the immortals
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,57);--/GEN pl of arrogant men (from demo)
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,37);--/GEN pl far from men that live by toil
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,20);--/DAT pl with many women
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,29);--/ACC pl Orfeus, stones and trees
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,62);--/ACC pl Musas

INSERT INTO "playlists" ("_id","description") VALUES (11,'Noun declensions (2)');
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (11,9);--/Peace, Order, Justice, and the Fates
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (11,15);--/αὐτοὺς ἐν τῷ Ταρτάρῳ - shut them into Tartarus
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (11,19);--/NOM pl Victory, Dominion, Emulation, and Violence


--..more..

INSERT INTO "playlists" ("_id","description") VALUES (5,'Reading list - quotes to learn verb forms');
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (5,11);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (5,10);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (5,22);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (5,23);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (5,24);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (5,25);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (5,26);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (5,27);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (5,31);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (5,32);

INSERT INTO "playlists" ("_id","description") VALUES (6,'Reading list - mythic and epic quotes');
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (6,7);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (6,8);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (6,33);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (6,34);

INSERT INTO "playlists" ("_id","description") VALUES (7,'Reading list - more quotes');
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (7,13);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (7,28);

INSERT INTO "playlists" ("_id","description") VALUES (9,'Recorded quotes');
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id","sorting") VALUES (9,56,1);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id","sorting") VALUES (9,59,1);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id","sorting") VALUES (9,60,1);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id","sorting") VALUES (9,57,2);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (9,36);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (9,37);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (9,38);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (9,39);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (9,40);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (9,58);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (9,41);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (9,42);

INSERT INTO "playlists" ("_id","description") VALUES (10,'Unallotted quotes to assign and annotate');
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (10,43);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (10,44);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (10,45);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (10,46);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (10,47);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (10,48);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (10,49);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (10,50);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (10,51);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (10,52);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (10,53);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (10,54);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (10,55);



--TODOs
--add/concat/merge/join column eec to description
--table: s_descriptions_translations (default in english), move it text in new table