package com.example.playground.repository

import com.example.playground.data.db.entity.MusicEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MainRepositoryMock {

    suspend fun getMusicas() : MutableList<MusicEntity>{
        return withContext(Dispatchers.Default){
            delay(3000)
            mutableListOf(
                MusicEntity(1, "Achilles Last Stand"),
                MusicEntity(2, "All My Love"),
                MusicEntity(
                    3,
                    "Babe I'm Gonna Leave You"
                ),
                MusicEntity(4, "Baby Come On Home"),
                MusicEntity(5, "Bathroom Sound"),
                MusicEntity(6, "Black Country Woman"),
                MusicEntity(7, "Black Dog"),
                MusicEntity(8, "Black Mountain Side"),
                MusicEntity(9, "Bonzo's Montreux"),
                MusicEntity(10, "Boogie with Stu"),
                MusicEntity(11, "Bring It On Home"),
                MusicEntity(12, "Bron-Y-Aur Stomp"),
                MusicEntity(13, "Bron-Yr-Aur"),
                MusicEntity(14, "C'mon Everybody"),
                MusicEntity(15, "Candy Store Rock"),
                MusicEntity(16, "Carouselambra"),
                MusicEntity(17, "Celebration Day"),
                MusicEntity(
                    18,
                    "Communication Breakdown"
                ),
                MusicEntity(19, "Custard Pie"),
                MusicEntity(20, "D'Yer Mak'er"),
                MusicEntity(21, "Dancing Days"),
                MusicEntity(22, "Darlene")/*,
                MusicEntity(23,"Dazed and Confused"),
                MusicEntity(24,"Down by the Seaside"),
                MusicEntity(25,"Fool in the Rain"),
                MusicEntity(26,"For Your Life"),
                MusicEntity(27,"Four Sticks"),
                MusicEntity(28,"Friends"),
                MusicEntity(29,"Gallows Pole"),
                MusicEntity(30,"Going to California"),
                MusicEntity(31,"Good Times Bad Times"),
                MusicEntity(32,"Hats Off to (Roy) Harper"),
                MusicEntity(33,"Heartbreaker"),
                MusicEntity(34,"Hey Hey What Can I Do"),
                MusicEntity(35,"Hot Dog"),
                MusicEntity(36,"Hots On for Nowhere"),
                MusicEntity(37,"Houses of the Holy"),
                MusicEntity(38,"How Many More Times"),
                MusicEntity(39,"I Can't Quit You Baby"),
                MusicEntity(40,"I'm Gonna Crawl"),
                MusicEntity(41,"Immigrant Song"),
                MusicEntity(42,"In My Time of Dying"),
                MusicEntity(43,"In The Evening"),
                MusicEntity(44,"In The Light"),
                MusicEntity(45,"Jennings Farm Blues"),
                MusicEntity(46,"Kashmir"),
                MusicEntity(47,"Key to the Highway/Trouble inMind"),
                MusicEntity(48,"LA Drone"),
                MusicEntity(49,"La La"),
                MusicEntity(50,"Living Loving Main (She'sJust a Woman)"),
                MusicEntity(51,"Misty Mountain Hop"),
                MusicEntity(52,"Moby Dick"),
                MusicEntity(53,"Night Flight"),
                MusicEntity(54,"No Quarter"),
                MusicEntity(55,"Nobody's Fault but Mine"),
                MusicEntity(56,"Out on the Tiles"),
                MusicEntity(57,"Over the Hills and Far Away"),
                MusicEntity(58,"Ozone Baby"),
                MusicEntity(59,"Poor Tom"),
                MusicEntity(60,"Ramble On"),
                MusicEntity(61,"Rock and Roll"),
                MusicEntity(62,"Royal Orleans"),
                MusicEntity(63,"Sick Again"),
                MusicEntity(64,"Since I've Been Loving You"),
                MusicEntity(65,"Somethin' Else"),
                MusicEntity(66,"South Bound Suarez"),
                MusicEntity(67,"Stairway to Heaven"),
                MusicEntity(68,"Tangerine"),
                MusicEntity(69,"Tea for One"),
                MusicEntity(70,"Ten Years Gone"),
                MusicEntity(71,"Thank You"),
                MusicEntity(72,"That's the Way"),
                MusicEntity(73,"The Battle of Evermore"),
                MusicEntity(74,"The Crunge"),
                MusicEntity(75,"The Girl I Love She Got LongBlack Wavy Hair"),
                MusicEntity(76,"The Lemon Song"),
                MusicEntity(77,"The Ocean"),
                MusicEntity(78,"The Rain Song"),
                MusicEntity(79,"The Rover"),
                MusicEntity(80,"The Song Remains the Same"),
                MusicEntity(81,"The Wanton Song"),
                MusicEntity(82,"Trampled Under Foot"),
                MusicEntity(83,"Travelling Riverside Blues"),
                MusicEntity(84,"Walter's Walk"),
                MusicEntity(85,"We're Gonna Groove"),
                MusicEntity(86,"Wearing and Tearing"),
                MusicEntity(87,"What Is and What ShouldNever Be"),
                MusicEntity(88,"When the Levee Breaks"),
                MusicEntity(89,"White Summer/Black MountainSide"),
                MusicEntity(90,"Whole Lotta Love"),
                MusicEntity(91,"You Shook Me"),
                MusicEntity(92,"Your Time Is Gonna Come")*/

            )
        }
    }
}