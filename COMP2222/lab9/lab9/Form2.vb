Public Class Form2
    Dim PanelPosition, CursorPoint As Point

    Private Sub Renew()
        PanelPosition = Panel1.Location
        CursorPoint = Cursor.Position
    End Sub

    Private Sub Timer1_Tick(sender As Object, e As EventArgs) Handles Timer1.Tick
        Panel1.Location = PanelPosition + (Cursor.Position - CursorPoint)
    End Sub

    Private Sub OvalShape_MouseDown(sender As Object, e As MouseEventArgs) Handles OvalShape1.MouseDown
        Timer1.Start()
        Renew()
    End Sub

    Private Sub OvalShape_MouseUp(sender As Object, e As MouseEventArgs) Handles OvalShape1.MouseUp
        Timer1.Stop()
    End Sub

End Class