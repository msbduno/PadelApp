<?php
     $_SESSION['current_time'] = new DateTime();
    $startDate = clone $_SESSION['current_time'];
    print("<label for=week, class=week-label> " );
    print($startDate->format('d/m/Y') . " to ");
    $startDate->modify('+2 day');
    print($startDate->format('d/m/Y') . "</label>");
?>