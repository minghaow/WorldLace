var $successModal = $("#successModal"),
    $successHdr = $("#successHdr"),
    $successMsg = $("#successMsg"),
    $failModal = $("#failModal"),
    $failHdr = $("#failHdr"),
    $failMsg = $("#failMsg");

function presentSuccessModal(header, message) {
    $successHdr.html(header);
    $successMsg.html(message);
    $successModal.foundation("open");
}

function presentFailModal(header, message) {
    $failHdr.html(header);
    $failMsg.html(message);
    $failModal.foundation('open');
}

function hideSuccessModal() {
    $successModal.foundation("close");
}

function hideFailModal() {
    $failModal.foundation('close');
}